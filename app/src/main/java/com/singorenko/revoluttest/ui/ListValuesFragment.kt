package com.singorenko.revoluttest.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.singorenko.revoluttest.R
import com.singorenko.revoluttest.network.remote.utilities.ApiUtils
import com.singorenko.revoluttest.ui.adapter.RecyclerViewRatesAdapter
import com.singorenko.revoluttest.ui.helper.UIHelper
import com.singorenko.revoluttest.ui.model.RateItem
import com.singorenko.revoluttest.util.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list_values.*
import java.util.concurrent.TimeUnit

class ListValuesFragment : Fragment() {
    private val TAG: String = "ExchangeListFragment"
    private var listRateItems: MutableList<RateItem>? = null
    private var disposable: Disposable? = null

    private var recyclerViewState: Parcelable? = null
    private lateinit var recyclerViewAdapter: RecyclerViewRatesAdapter

    private var disposableRequestData: Disposable? = null

    private lateinit var currencySelected : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_values, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencySelected = UIHelper.getPreferenceCurrency(context)

        val layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        rv_rate_list.setHasFixedSize(true)
        rv_rate_list.layoutManager = layoutManager
    }

    private fun fillRecyclerView() {
        recyclerViewAdapter = RecyclerViewRatesAdapter(listRateItems, context)
        rv_rate_list.adapter = recyclerViewAdapter
    }

    override fun onResume() {
        super.onResume()
        //Restore recyclerView State
        (rv_rate_list.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerViewState)

        disposableRequestData = Observable.interval(Constants.PERIOD_UPDATE_FROM_SERVER, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe { dataRequest() }
    }

    private fun dataRequest() {
        //Save recyclerView State Position to avoid auto scroll up with each update from server
        recyclerViewState = (rv_rate_list.layoutManager as LinearLayoutManager).onSaveInstanceState()

        disposable = ApiUtils.getApiService().getRateList(Constants.usdShortName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d(TAG, "called successfully")
                    listRateItems = UIHelper.fillListRareItems(result.rates)

                    //Restore recyclerView State
                    (rv_rate_list.layoutManager as LinearLayoutManager).onRestoreInstanceState(recyclerViewState)

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
        recyclerViewState = (rv_rate_list.layoutManager as LinearLayoutManager).onSaveInstanceState()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListValuesFragment().apply {}
    }
}