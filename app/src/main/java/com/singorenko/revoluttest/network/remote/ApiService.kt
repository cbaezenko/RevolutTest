package com.singorenko.revoluttest.network.remote

import com.singorenko.revoluttest.network.model.RatesRequest
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/latest")
    fun getRateList(
        @Query("base", encoded = true) base: String
    ): Observable<RatesRequest>
}