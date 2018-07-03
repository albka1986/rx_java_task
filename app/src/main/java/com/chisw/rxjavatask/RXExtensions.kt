package com.chisw.rxjavatask

import android.util.Log
import com.chisw.rxjavatask.MainActivity.Companion.TAG
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Ponomarenko Oleh on 22.06.2018.
 */

fun <T> Single<T>.log(): Single<T> {
    return map {
        Log.e(TAG, "Current thread: ${Thread.currentThread().name}")
        return@map it
    }
}

fun <T> Single<T>.subscribeCustom(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}


fun <T> Observable<T>.subscribeCustom(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}


fun <T> Maybe<T>.subscribeCustom(): Maybe<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}


fun safeSubscribe(disposable: Disposable) {
    val compositeDisposable = CompositeDisposable()
    compositeDisposable.add(disposable)
}




