package com.app.josuelopez.travelbuddy.data.remote.dto

import androidx.annotation.Keep

@Keep
data class UserResponse(
    val user: UserDetailResponse,
    val token: String?,
)

@Keep
data class UserDetailResponse(
    val _id: String,
    val name: String,
    val email: String,
)














