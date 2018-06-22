package com.chisw.rxjavatask

import android.util.Log
import io.reactivex.SingleSource

/**
 * Created by Ponomarenko Oleh on 22.06.2018.
 */

fun SingleSource<Any>.log() {
    Log.e("result", "Current thread: ${Thread.currentThread().stackTrace}")
}