package com.chisw.rxjavatask

import android.util.Log
import com.chisw.rxjavatask.MainActivity.Companion.TAG
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ponomarenko Oleh on 22.06.2018.
 */

fun <T> Single<T>.log(): Single<T> {
    Log.e(TAG, "Current thread: ${Thread.currentThread().name}")
    return this
}

fun <T> Single<T>.subscribeCustom(): Single<T> {
    this.subscribeOn(Schedulers.io())
    this.observeOn(AndroidSchedulers.mainThread())
    return this
}


fun <T> Observable<T>.subscribeCustom(): Observable<T> {
    this.subscribeOn(Schedulers.io())
    this.observeOn(AndroidSchedulers.mainThread())
    return this
}


fun <T> Maybe<T>.subscribeCustom(): Maybe<T> {
    this.subscribeOn(Schedulers.io())
    this.observeOn(AndroidSchedulers.mainThread())
    return this
}

fun <T> Single<T>.safeSubscribe(): Disposable {
    this.subscribeOn(Schedulers.io())
    this.observeOn(AndroidSchedulers.mainThread())
    return this.subscribe(
            { Log.e(TAG, "successful") },
            { Log.e(TAG, "failed") }
    )
}




