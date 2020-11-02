package com.app.travelbuddy.ui.model

import android.os.Parcelable
import com.app.travelbuddy.ui.model.enumeration.TypeTipModel
import kotlinx.android.parcel.Parcelize

@Parcelize
class TipModel(
    val type: TypeTipModel,
    val description: String,
    val value: String,
) : Parcelable

