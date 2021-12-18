package com.think.searchimage.extentions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.flow.*


    suspend fun <T> Flow<T>.subscribeThem(
        onError: (exception: Throwable) -> Unit,
        onNext: (T) -> Unit,
        onComplete: () -> Unit,
        onCollect:(T)->Unit
    ) {

        this.onEach {
            onNext(it)
        }
            .catch { throwable->
                onError(throwable)
            }
            .onCompletion { cause: Throwable? ->
                if(cause==null){
                    onComplete()
                }
            }
            .collect {
                onCollect(it)
            }

    }
    fun Context.showToastLong( message: String) {
        this?.let {
            (this as Activity).runOnUiThread {
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

