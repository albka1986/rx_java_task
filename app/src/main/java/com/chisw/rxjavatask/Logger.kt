package com.chisw.rxjavatask

import android.util.Log
import com.chisw.rxjavatask.MainActivity.Companion.TAG
import com.chisw.rxjavatask.model.Item
import io.reactivex.Single

/**
 * Created by Ponomarenko Oleh on 22.06.2018.
 */

fun Single<Item>.log(): Single<Item> {
    Log.e(TAG, "Current thread: ${Thread.currentThread().name}")
    return this
}