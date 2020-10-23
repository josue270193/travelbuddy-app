package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(
    @PrimaryKey val id: String,
    val name: String,
    val ranking: Double
) {
    var countryId: Long? = null
    var imageUrl: String? = null
}