package com.app.josuelopez.travelbuddy.data.remote

import com.app.josuelopez.travelbuddy.utils.Resource
import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}", response.code())
        } catch (e: Exception) {
            return error(e.message ?: e.toString(), 0)
        }
    }

    private fun <T> error(message: String, statusCode: Int): Resource<T> {
        Timber.d(message)
        return Resource.error(
            "Network call has failed for a following reason: $message",
            null,
            statusCode
        )
    }

}