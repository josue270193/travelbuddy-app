package com.app.travelbuddy.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import com.app.travelbuddy.data.local.SharedPreferenceStringLiveData
import com.app.travelbuddy.data.local.dao.UserDao
import com.app.travelbuddy.data.remote.UserRemoteDataSource
import com.app.travelbuddy.data.remote.dto.UserRequest
import com.app.travelbuddy.utils.ConstantUtil
import com.app.travelbuddy.utils.Resource
import com.app.travelbuddy.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserDao,
    private val sharedPreferences: SharedPreferences
) {

    fun signIn(email: String, password: String) = networkBoundResource(
        fetchFromLocal = {
            localDataSource.getUserByEmail(email)
        },
        shouldFetchFromRemote = {
            it == null
        },
        fetchFromRemote = {
            remoteDataSource.postSignIn(UserRequest(email, password))
        },
        processRemoteResponse = { },
        saveRemoteData = {
            localDataSource.insertUserFull(it)
        },
        onFetchFailed = { _, _ -> }
    )

    fun signUp(email: String, password: String, name:String) = networkBoundResource(
        fetchFromLocal = {
            localDataSource.getUserByEmail(email)
        },
        shouldFetchFromRemote = {
            it == null
        },
        fetchFromRemote = {
            remoteDataSource.postSignUp(UserRequest(email, password, name))
        },
        processRemoteResponse = { },
        saveRemoteData = {
            localDataSource.insertUserFull(it)
        },
        onFetchFailed = { _, _ -> }
    )

    fun getUserLogin() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val localData = localDataSource.getUsers().asFlow().first()
            val userLogin = localData.first()
            emit(Resource.success(userLogin))
        } catch (error: Exception) {
            emit(Resource.error("No user", null, 404))
        }
    }
}