package com.app.josuelopez.travelbuddy.data.remote.dto

import androidx.annotation.Keep

@Keep
data class CityDetailResponse(
    val country: String,
    val city: String,
    val entity_type: String,
    val score_average: Number,
    val entity_value: List<String>,
    val reviews: List<CityDetailReviewResponse>,
)

@Keep
data class CityDetailReviewResponse(
    val text: String,
    val score: Number,
)
