package com.app.travelbuddy.data.remote

import com.app.travelbuddy.data.remote.dto.UserRequest
import com.app.travelbuddy.data.remote.service.UserService
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) : BaseDataSource() {

    suspend fun getUser() =
        getResult { userService.getUser() }

    suspend fun postSignIn(userRequest: UserRequest) =
        getResult { userService.postSignIn(userRequest) }

    suspend fun postSignUp(userRequest: UserRequest) =
        getResult { userService.postSignUp(userRequest) }
}