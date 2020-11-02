package com.app.travelbuddy.data.remote.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserRequest(
    val email: String,
    val password: String,
    val name: String? = null
)















