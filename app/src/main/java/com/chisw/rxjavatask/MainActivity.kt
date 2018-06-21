package com.chisw.rxjavatask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.chisw.rxjavatask.model.Item
import com.chisw.rxjavatask.model.Story
import com.chisw.rxjavatask.network.ApiService
import io.reactivex.Observable
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
//        taskThird()
    }

    /**
    Load stories from the 1 and 2 pages
    Print a list of titles from two pages
     */
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

    /**
    Get list of stories from page 0
    For all stories load author’s info
    Print only authors with karma greater than 3000
     */
    private fun taskSecond() {
        apiService.getStoriesByPage(0)
                .subscribeOn(Schedulers.io())
                .toObservable()
                .flatMap { Observable.fromIterable(it.hits.asIterable()).map { t: Story -> apiService.getUserByName(t.author) } }
                .toList()
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


    /**
     * Create a maybe source, that emits “Bang!” string and completes or finishes with an IllegalArgumentException.
     * Use randomizer to decide what to emit.
     */
    private fun taskThird() {


    }


}
