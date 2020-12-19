package com.app.josuelopez.travelbuddy.utils

import androidx.annotation.Keep

@Keep
data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val statusCode: Int?
) {

    @Keep
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null, 200)
        }

        fun <T> error(message: String, data: T? = null, statusCode: Int): Resource<T> {
            return Resource(Status.ERROR, data, message, statusCode)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null, null)
        }
    }
}