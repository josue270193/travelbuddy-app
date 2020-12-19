package com.app.josuelopez.travelbuddy.data.remote.dto

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonInclude

@Keep
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserRequest(
    val email: String,
    val password: String,
    val name: String? = null
)















