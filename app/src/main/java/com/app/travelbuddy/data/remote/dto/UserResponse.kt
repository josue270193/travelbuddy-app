package com.app.travelbuddy.data.remote.dto

data class UserResponse(
    val user: UserDetailResponse,
    val token: String?,
)

data class UserDetailResponse(
    val _id: String,
    val name: String,
    val email: String,
)














