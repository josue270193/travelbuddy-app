package com.app.travelbuddy.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

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