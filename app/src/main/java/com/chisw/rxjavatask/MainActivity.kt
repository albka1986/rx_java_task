package com.chisw.rxjavatask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.chisw.rxjavatask.model.Item
import com.chisw.rxjavatask.network.ApiService
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG: String = "result"
    }

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
//        taskSecond()
//        taskThird()
//        taskFourth()
//        taskFifth()
    }

    /**
    Load stories from the 1 and 2 pages
    Print a list of titles from two pages
     */
    @Suppress("unused")
    private fun taskFirst() {
        Single.zip(apiService.getStoriesByPage(0), apiService.getStoriesByPage(1), BiFunction<Item, Item, List<String>> { list1, list2 ->
            list1.hits.plus(list2.hits).map { it.title }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e(TAG, result.toString())
                            Log.e(TAG, result.size.toString())
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
    @Suppress("unused")
    private fun taskSecond() {
        apiService.getStoriesByPage(0)
                .map { it.hits }
                .toObservable()
                .flatMapIterable { it }
                .flatMap { apiService.getUserByName(it.author).toObservable() }
                .filter { it.karma > 3000 }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.e(TAG, result.toString())
                        },
                        { error ->
                            Log.e("test", error.message)
                        }
                )

    }

    /**
     * Create a maybe source, that emits “Bang!” string or finishes with an IllegalArgumentException.
     * Use randomizer to decide what to emit.
     */
    @Suppress("unused")
    private fun taskThird() {
        Maybe.create<String> { emitter ->
            if (Random().nextBoolean()) {
                emitter.onSuccess("Bang!")
            } else {
                emitter.onError(IllegalArgumentException())
            }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.e(TAG, "error", it) }
                .doOnSuccess { Log.e(TAG, it) }
                .subscribe()
    }

    /**
     * Create a maybe source, that emits “Bang!” string and completes or completes without emitting.
     * Use randomizer to decide what to emit.
     */
    @Suppress("unused")
    private fun taskFourth() {
        Maybe.create<String> { emitter ->
            if (Random().nextBoolean()) {
                emitter.onSuccess("Bang!")
            } else {
                emitter.onComplete()
            }
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { Log.e(TAG, "doOnComplete") }
                .doOnSuccess { Log.e(TAG, it) }
                .subscribe()

    }

    /**
     * Use maybe source from the previous task
     * Transform it to single
     * Print “Bang” from maybe or "You're live" in other case
     */
    @Suppress("unused")
    private fun taskFifth() {
        Maybe.create<String> { emitter ->
            if (Random().nextBoolean()) {
                emitter.onSuccess("Bang!")
            } else {
                emitter.onComplete()
            }
        }.toSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.e(TAG, it) },
                        { Log.e(TAG, "You're live") }
                )
    }




}
