package com.chisw.rxjavatask

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.chisw.rxjavatask.model.Item
import com.chisw.rxjavatask.network.ApiService
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "result"
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
//        taskSixth()
//        taskSeventh()
        taskEighth()
//        taskNinth()
//        taskTenth()
//        taskEleventh()
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

    /**
     * Task 6.1
     *  Create an extension to SingleSource that logs current thread. Should work similar to RxJava operators.
     *  Should print “Current thread: thread name”
     *
     *  Task 6.2
     *  Create an extension to SingleSource, ObservableSource and MaybeSource that subscribes on Schedulers.io() and observes on AndroidSchedulers.mainThread()
     *
     *  Task 6.3
     *  Create a function that accepts disposable and adds it to a CompositeDisposable.
     *  Example:
     *  safeSubscribe{
     *  observable.subscribeOn(...).observeOn(...).subscribe(...)
     *  }
     */
    @Suppress("unused")
    private fun taskSixth() {
        taskSixth1()
        taskSixth2()
        taskSix3()
    }

    private fun taskSixth1() {
        apiService.getStoriesByPage(0).log()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.e(TAG, "successful") },
                        { Log.e(TAG, "failed") }
                )
    }

    private fun taskSixth2() {
        apiService.getStoriesByPage(0)
                .subscribeCustom()
                .subscribe(
                        { Log.e(TAG, "successful") },
                        { Log.e(TAG, it.localizedMessage) }
                )

    }

    private fun taskSix3() {
        safeSubscribe(apiService.getStoriesByPage(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { Log.e(TAG, "successful") },
                        { Log.e(TAG, it.localizedMessage) }
                ))
    }

    /**
     * Create an observable that emits values from 0 to 10 every second.
     * Accumulate every 2 values
     * Load stories with a page equals to the emitted value
     */
    @Suppress("unused")
    private fun taskSeventh() {
        Observable.zip(Observable.range(0, 10), Observable.interval(1000L, TimeUnit.MILLISECONDS), BiFunction<Int, Long, Int> { t1: Int, _: Long ->
            t1
        })
                .filter { t -> t % 2 != 0 }
                .toList()
                .map { it.sum() }
                .flatMap {
                    Log.e(TAG, "Number of the page: $it")
                    apiService.getStoriesByPage(it)
                }
                .subscribe(
                        { Log.e(TAG, "Successful") },
                        { Log.e(TAG, it.localizedMessage) }
                )
    }

    /**
     *  Create 3 executors
     *  Load stories from page 0 in the thread 1
     *  Log thread
     *  Load stories from page 1 in the thread 2
     *  Log thread
     *  Subscribe in the thread 3
     *  Print a list of titles from two pages and the name of the thread
     */
    @Suppress("unused")
    private fun taskEighth() {
        ///TODO: "not implemented taskEighth
    }

    /**
     * Create an observable that emits values from 0 to 10 every second
     * Prints “Subscribed” when it is subscribed and prints values in onNext()
     * In onError it prints an error message
     * When the value 7 is emitted it should throw Exception(“Your error message”)
     */
    @Suppress("unused")
    private fun taskNinth() {
        //TODO: "not implemented- taskNinth")
    }

    /**
     * Choose the most suitable Subject for the next task
     * Create Subject<Int>
     *     Post values 1,2,3
     *     Subscribe to it
     *     Post values 4, 5, 6
     *     In the cmd “3,4,5,6” should be printed
     */
    @Suppress("unused")
    private fun taskTenth() {
        //TODO: "not implemented- taskTenth")
    }

    /**
     * Load stories from the page 0
     * Load 3rd story’s author info
     * Combine to the new model with fields: author name, karma, story title
     * Print the result
     */
    @Suppress("unused")
    private fun taskEleventh() {
        //TODO: "not implemented - taskEleventh"
    }


}
