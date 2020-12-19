package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "country")
data class Country(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}