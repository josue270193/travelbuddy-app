package com.app.travelbuddy.data.remote.dto

data class CityDetailResponse(
    val country: String,
    val city: String,
    val entity_type: String,
    val score_average: Number,
    val entity_value: List<CityDetailReviewResponse>,
)

data class CityDetailReviewResponse(
    val text: String,
    val score: Number,
)
