package com.chisw.rxjavatask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.chisw.rxjavatask.model.Item
import com.chisw.rxjavatask.network.ApiService
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
        click_btn.setOnClickListener { getStoriesFromTwoPages() }
    }

    private fun getStoriesFromTwoPages() {
        apiService.getStoriesByPage(0)
                .zipWith(apiService.getStoriesByPage(1), BiFunction<Item, Item, Array<String>> { list1, list2 ->
                    return@BiFunction arrayOf(list1.hits.plus(list2.hits).joinToString("\n") { story -> story.title })
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.d("getStoriesFromTwoPages", result.joinToString("\n"))
                        },
                        { error ->
                            Log.e("test", error.message)
                        }
                )


    }


}
