package com.singorenko.revoluttest.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.network.remote.utilities.ApiUtils
import com.singorenko.revoluttest.ui.adapter.RecyclerViewAdapter
import com.singorenko.revoluttest.ui.helper.UIHelper
import com.singorenko.revoluttest.ui.model.RateItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import android.text.Editable
import android.text.TextWatcher
import com.singorenko.revoluttest.util.Constants
import kotlinx.android.synthetic.main.fragment_exchange_list.*
import kotlinx.android.synthetic.main.item_value_exchange.*

class ExchangeListFragment : Fragment(), RecyclerViewAdapter.ListItemClickListener {
    override fun onListItemClick(clickedItemIndex: Int) {
        UIHelper.savePreferenceCurrency(this.listRateItems!![clickedItemIndex].currencyAbb, this.context!!)
        UIHelper.setPreferenceCurrency(context, tv_money_description, tv_money_short_name, iv_money_image)
    }

    private val TAG: String = "ExchangeListFragment"
    private var listRateItems: MutableList<RateItem>? = null
    private var disposable: Disposable? = null
    private lateinit var layoutManager: LinearLayoutManager

    private var recyclerViewState: Parcelable? = null
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    private var disposableRequestData: Disposable? = null

    private var amount: Float = 1.0F
    private lateinit var currencySelected : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exchange_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencySelected = UIHelper.getPreferenceCurrency(context)

        layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        rv_exchange_list.setHasFixedSize(true)
        rv_exchange_list.layoutManager = layoutManager
        et_currency.addTextChangedListener(mTextEditorWatcher)
    }

    private fun fillRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(listRateItems, this, context, amount)
        rv_exchange_list.adapter = recyclerViewAdapter
    }

    override fun onResume() {
        super.onResume()

        UIHelper.setPreferenceCurrency(context, tv_money_description, tv_money_short_name, iv_money_image)

        //Restore recyclerView State
        (rv_exchange_list.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerViewState)

        amount = (et_currency.editableText.toString().replace(',', '.')).toFloat()

        disposableRequestData = Observable.interval(Constants.PERIOD_UPDATE_FROM_SERVER, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()
            ).subscribe { dataRequest() }
    }

    private fun dataRequest (){
        //Save recyclerView State Position to avoid auto scroll up with each update from server
        recyclerViewState = (rv_exchange_list.layoutManager as LinearLayoutManager).onSaveInstanceState()
        disposable = ApiUtils.getApiService().getRateList(currencySelected)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d(TAG, "called successfully")
                    listRateItems = UIHelper.fillListRareItems(result.rates)

                    //Restore recyclerView State
                    (rv_exchange_list.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerViewState)

                    //Fill recyclerView when the data is received from server side
                    fillRecyclerView()
                },
                { error ->
                    Log.d(TAG, "error on call: " + error.message)
                }
            )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
        disposableRequestData?.dispose()

        //save recyclerViewState position
        recyclerViewState = (rv_exchange_list.layoutManager as LinearLayoutManager).onSaveInstanceState()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExchangeListFragment().apply {}
    }

    private val mTextEditorWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if(et_currency.editableText.toString().isNotEmpty()) {
                amount = (et_currency.editableText.toString().replace(',', '.')).toFloat()
                dataRequest()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }
}