package com.think.searchimage.network.newNetwork


import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class NetworkCallback<T> : Callback<T> {
    abstract fun onResponse(result: Result<T>)
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful && response.body() != null) {
            onResponse(getSuccessResult(response))

        } else {
            onResponse(getFailureResult(response))
        }
    }

    private fun getFailureResult(response: Response<T>): Result<T> {
        val failureResponse = FailureResponse(response.code(), response.message(), FailureResponse.Status.UNKNOWN_ERROR)
        return Result(failureResponse, Result.Status.FAILURE)
    }

    private fun getSuccessResult(response: Response<T>): Result<T> {
        return if (response.body() == null) {
            Result(response.code(), response.message() .toString(), null, Result.Status.SUCCESS)
        } else Result(response.body()!!, response.code(), response.message(), null, Result.Status.SUCCESS)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(TAG, "onFailure: ${t.message}")
        if (t is SocketTimeoutException || t is UnknownHostException) {
            onResponse(noNetworkFailureResult)
        } else if (t is ConnectException) {
            onResponse(failedToConnectFailureResult)
        } else if (!call.isCanceled) {
            onResponse(getFailureResult())
        }
    }

    private val failedToConnectFailureResult: Result<T>
        get() {
            Log.e(TAG, "getNoNetworkFailureResult: Failed to connect error")
            val failureResponse = FailureResponse(0, "Fail to connect", FailureResponse.Status.CONNECTION_FAILED)
            return Result(failureResponse, Result.Status.FAILURE)
        }

    private val noNetworkFailureResult: Result<T>
        get() {
            Log.e(TAG, "getNoNetworkFailureResult: No internet error")
            val failureResponse = FailureResponse(101, "No Internet", FailureResponse.Status.NO_INTERNET)
            return Result(failureResponse, Result.Status.FAILURE)
        }

    private fun getFailureResult(): Result<T> {
        val failureResponse = FailureResponse(0, "Something went wrong", FailureResponse.Status.UNKNOWN_ERROR)
        return Result(failureResponse, Result.Status.FAILURE)
    }

    companion object {
        private const val TAG = "NetworkCallback"
    }
}