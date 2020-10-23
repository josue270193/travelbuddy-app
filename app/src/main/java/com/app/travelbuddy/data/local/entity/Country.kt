package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}