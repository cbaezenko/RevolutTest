package com.singorenko.revoluttest.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.network.remote.utilities.ApiUtils
import com.singorenko.revoluttest.ui.adapter.RecyclerViewAdapter
import com.singorenko.revoluttest.ui.helper.UIHelper
import com.singorenko.revoluttest.ui.model.RateItem
import com.singorenko.revoluttest.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.item_value_exchange.*

class ExchangeListFragment : Fragment(), RecyclerViewAdapter.ListItemClickListener {
    override fun onListItemClick(clickedItemIndex: Int) {
        UIHelper.savePreferenceCurrency(this.listRateItems!![clickedItemIndex].currencyAbb, this.context!!)
        UIHelper.setPreferenceCurrency(context, tv_money_description, tv_money_short_name)
    }

    private val TAG: String = "ExchangeListFragment"
    private var listRateItems: MutableList<RateItem>? = null
    private var disposable: Disposable? = null
    private lateinit var layoutManager: LinearLayoutManager

    private var recyclerViewState: Parcelable? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    private var disposableRequestData: Disposable? = null

    private var amount: Float = 1.0F
    private lateinit var etAmountValue: EditText

    private lateinit var currencySelected : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exchange_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencySelected = UIHelper.getPreferenceCurrency(context)

        Log.d(TAG, "show exchange list")

        recyclerView = view.findViewById<View>(R.id.rv_exchange_list) as RecyclerView
        layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager

        etAmountValue = view.findViewById(R.id.et_currency)
        etAmountValue.addTextChangedListener(mTextEditorWatcher)
    }

    private fun fillRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(listRateItems, this, context, amount)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onResume() {
        super.onResume()

        UIHelper.setPreferenceCurrency(context, tv_money_description, tv_money_short_name)

        //Restore recyclerView State
        (recyclerView.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerViewState)

        amount = (etAmountValue.editableText.toString().replace(',', '.')).toFloat()

        disposableRequestData = Observable.interval(10, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()
            ).subscribe { dataRequest() }
    }

    private fun dataRequest (){
        //Save recyclerView State Position to avoid auto scroll up with each update from server
        recyclerViewState = (recyclerView.layoutManager as LinearLayoutManager).onSaveInstanceState()
        disposable = ApiUtils.getApiService().getRateList(currencySelected)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d(TAG, "called successfully")
                    listRateItems = UIHelper.fillListRareItems(result.rates)

                    //Restore recyclerView State
                    (recyclerView.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerViewState)

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
        recyclerViewState = (recyclerView.layoutManager as LinearLayoutManager).onSaveInstanceState()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExchangeListFragment().apply {}
    }

    private val mTextEditorWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if(etAmountValue.editableText.toString().isNotEmpty()) {
                amount = (etAmountValue.editableText.toString().replace(',', '.')).toFloat()
                dataRequest()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }
}