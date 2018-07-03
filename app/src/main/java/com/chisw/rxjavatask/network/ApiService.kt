package com.chisw.rxjavatask.network

import com.chisw.rxjavatask.model.Item
import com.chisw.rxjavatask.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Ponomarenko Oleh on 14.06.2018.
 */
interface ApiService {

    @GET("search_by_date?tags=story&")
    fun getStoriesByPage(@Query("page") page: Int): Single<Item>

    @GET("users/{username}")
    fun getUserByName(@Path("username") username: String): Single<User>

}