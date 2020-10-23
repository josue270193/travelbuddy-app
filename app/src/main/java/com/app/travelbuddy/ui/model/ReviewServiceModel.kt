package com.app.travelbuddy.ui.model

import java.util.*

class ReviewServiceModel(
    val review: String,
    val ranking: Double,
    val date: Date? = null,
    val user: String? = null,
)
