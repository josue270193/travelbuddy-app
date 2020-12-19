package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation

@Keep
data class CountryFull(
    @Embedded val country: Country,

    @Relation(
        parentColumn = "id",
        entityColumn = "countryId",
        entity = City::class
    )
    val cities: List<CityFull>
) {
    @Relation(
        parentColumn = "id",
        entityColumn = "countryId",
        entity = Wiki::class
    )
    var wiki: WikiFull? = null
}