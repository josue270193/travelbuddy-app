package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

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