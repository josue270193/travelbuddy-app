package com.app.travelbuddy.data.remote.service

import com.app.travelbuddy.data.remote.dto.UserRequest
import com.app.travelbuddy.data.remote.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @GET("users/me")
    suspend fun getUser(): Response<UserResponse>

    @POST("auth/signin")
    suspend fun postSignIn(@Body request: UserRequest): Response<UserResponse>

    @POST("auth/signup")
    suspend fun postSignUp(@Body request: UserRequest): Response<UserResponse>
}