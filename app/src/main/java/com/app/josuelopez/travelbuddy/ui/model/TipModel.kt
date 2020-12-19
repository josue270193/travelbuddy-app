package com.app.josuelopez.travelbuddy.ui.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.app.josuelopez.travelbuddy.ui.model.enumeration.TypeTipModel
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
class TipModel(
    val type: TypeTipModel,
    val description: String,
    val value: String,
) : Parcelable

