package com.app.travelbuddy.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CityModel(
    val id: String,
    val city: String,
    val country: String,
    val ranking: Double,
    val imageUrl: String?,
    val tips: List<TipModel>?
) : Parcelable
