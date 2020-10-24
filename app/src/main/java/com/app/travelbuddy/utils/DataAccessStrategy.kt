package com.app.travelbuddy.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first

fun <T, A> performGetOperation(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map {
            Resource.success(it)
        }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!, null, responseStatus.statusCode!!))
            emitSource(source)
        }
    }

fun <DB, REMOTE> networkBoundResource(
    fetchFromLocal: () -> LiveData<DB>,
    shouldFetchFromRemote: (DB?) -> Boolean = { true },
    fetchFromRemote: suspend () -> Resource<REMOTE>,
    processRemoteResponse: (response: Resource<REMOTE>) -> Unit = { Unit },
    saveRemoteData: (REMOTE) -> Unit = { Unit },
    onFetchFailed: (errorBody: String?, statusCode: Int) -> Unit = { _: String?, _: Int -> Unit }
): LiveData<Resource<DB>> = liveData(Dispatchers.IO) {

    emit(Resource.loading(null))
    val localData = fetchFromLocal.invoke().asFlow().first()
    if (shouldFetchFromRemote(localData)) {
        emit(Resource.loading(localData))
        val invoke = fetchFromRemote.invoke()
        when (invoke.status) {
            Resource.Status.SUCCESS -> {
                processRemoteResponse(invoke)
                invoke.data?.let { saveRemoteData(it) }
                emitSource(fetchFromLocal().map { dbData ->
                    Resource.success(dbData)
                })
            }
            Resource.Status.ERROR -> {
                onFetchFailed(invoke.message, invoke.statusCode!!)
                emitSource(fetchFromLocal().map {
                    Resource.error(
                        invoke.message!!,
                        it,
                        invoke.statusCode
                    )
                })
            }
            else -> {
            }
        }
    } else {
        emitSource(fetchFromLocal().map { Resource.success(it) })
    }
}