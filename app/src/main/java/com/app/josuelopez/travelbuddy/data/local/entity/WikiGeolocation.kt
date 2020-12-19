package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "wiki_geolocation")
data class WikiGeolocation(
    val description: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val precision: Double? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var wikiId: Long? = null
}