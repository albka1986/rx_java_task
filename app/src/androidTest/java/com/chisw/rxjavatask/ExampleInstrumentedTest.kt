package com.chisw.rxjavatask

import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.chisw.rxjavatask.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val apiService by lazy {
        ApiService.create()
    }

    @Test
    fun useAppContext() {
        apiService.getStoriesByPage(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->

                            Log.e("test", result.toString())
                        },
                        { error ->
                            Log.e("test", error.message)
                        }
                )

    }
}
