package com.chisw.rxjavatask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.chisw.rxjavatask.model.Item
import com.chisw.rxjavatask.network.ApiService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val apiService by lazy {
        ApiService.create()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        click_btn.setOnClickListener { onBtnPressed() }
    }

    private fun onBtnPressed() {
//        taskFirst()
        taskSecond()

    }

    private fun taskSecond() {
        apiService.getStoriesByPage(0)
                .flattenAsObservable {
                    (it.hits.asIterable()).map { apiService.getUserByname(it.author) }
                }
                .subscribeOn(Schedulers.io())

                .observeOn(AndroidSchedulers.mainThread())


                .subscribe(
                        { result ->
                            Log.d("onBtnPressed", result.toString())
                        },
                        { error ->
                            Log.e("test", error.message)
                        }
                )

    }

    private fun taskFirst() {
        Single.zip(apiService.getStoriesByPage(0), apiService.getStoriesByPage(1), BiFunction<Item, Item, List<String>> { list1, list2 ->
            list1.hits.plus(list2.hits).map { it.title }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.d("onBtnPressed", result.toString())
                            Log.d("onBtnPressed", result.size.toString())
                        },
                        { error ->
                            Log.e("test", error.message)
                        }
                )
    }


}
