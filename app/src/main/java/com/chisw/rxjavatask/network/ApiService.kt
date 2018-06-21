package com.chisw.rxjavatask.network

import com.chisw.rxjavatask.BuildConfig
import com.chisw.rxjavatask.model.Item
import com.chisw.rxjavatask.model.User
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ponomarenko Oleh on 14.06.2018.
 */
interface ApiService {

    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }

    @GET("search_by_date?tags=story&")
    fun getStoriesByPage(@Query("page") page: Int): Single<Item>

    @GET("users/{username}")
    fun getUserByName(@Path("username") username: String): Single<User>

}