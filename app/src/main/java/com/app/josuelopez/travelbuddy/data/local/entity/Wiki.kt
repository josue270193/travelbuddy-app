package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "wiki")
data class Wiki(
    val alias: String? = null,
    val labels: String? = null,
    val description: String? = null,
    val emoji: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var countryId: Long? = null
    var cityId: String? = null
}