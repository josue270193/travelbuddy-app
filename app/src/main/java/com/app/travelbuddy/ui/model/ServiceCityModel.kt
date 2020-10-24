package com.app.travelbuddy.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ServiceCityModel(
    val title: String,
    val ranking: Double,
    val reviewAmount: Int,
    val reviews: List<ServiceCityReviewModel>? = listOf(),
    val image: Int? = null
) : Parcelable

@Parcelize
class ServiceCityReviewModel(
    val text: String,
    val ranking: Double,
) : Parcelable