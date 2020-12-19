package com.app.josuelopez.travelbuddy.ui.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
class CityModel(
    val id: String,
    val city: String,
    val country: String,
    val ranking: Double,
    val imageUrl: String?,
    val tips: List<TipModel>?
) : Parcelable
