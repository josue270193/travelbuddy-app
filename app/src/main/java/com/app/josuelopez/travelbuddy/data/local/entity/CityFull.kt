package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation

@Keep
data class CityFull(
    @Embedded val city: City
) {
    @Relation(
        parentColumn = "id",
        entityColumn = "cityId",
        entity = Wiki::class
    )
    var wiki: WikiFull? = null
}