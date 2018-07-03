package com.chisw.rxjavatask.network

import com.chisw.rxjavatask.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Ponomarenko Oleh on 03.07.2018.
 */
object RetrofitManager {

    var apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build()
        apiService = retrofit.create(ApiService::class.java)
    }
}