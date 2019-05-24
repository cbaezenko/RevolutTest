package com.singorenko.revoluttest.network.remote.utilities

import com.singorenko.revoluttest.network.remote.ApiService
import com.singorenko.revoluttest.network.remote.RetrofitClient

class ApiUtils {

    companion object {
        fun getApiService(): ApiService {
            val baseUrl = "https://revolut.duckdns.org"
            return RetrofitClient.create(baseUrl).create(ApiService::class.java)
        }
    }
}