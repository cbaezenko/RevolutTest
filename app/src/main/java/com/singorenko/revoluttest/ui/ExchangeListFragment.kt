package com.singorenko.revoluttest.ui

import android.os.Bundle
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
import com.singorenko.revoluttest.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ExchangeListFragment : Fragment() {

    private val TAG: String = "ExchangeListFragment"
    private var listRateItems: MutableList<RateItem>? = null
    private var disposable: Disposable? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exchange_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<View>(R.id.rv_exchange_list) as RecyclerView
        val layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
    }

    private fun fillRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(listRateItems)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onResume() {
        super.onResume()

        disposable = ApiUtils.getApiService().getRateList(Constants.usdShortName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.d(TAG, "called successfully")
                    listRateItems = UIHelper.fillListRareItems(result.rates)

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
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExchangeListFragment().apply {
        }
    }
}