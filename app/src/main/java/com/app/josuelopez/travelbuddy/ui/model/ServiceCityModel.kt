package com.app.josuelopez.travelbuddy.ui.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
class ServiceCityModel(
    val title: String,
    val ranking: Double,
    val reviewAmount: Int,
    val entities: List<String>,
    val reviews: List<ServiceCityReviewModel>? = listOf(),
    val image: Int? = null,
    val banner: Int? = null
) : Parcelable

@Keep
@Parcelize
class ServiceCityReviewModel(
    val text: String,
    val ranking: Double,
) : Parcelable
