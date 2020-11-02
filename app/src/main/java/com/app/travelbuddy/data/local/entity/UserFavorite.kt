package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_favorites")
class UserFavorite {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var userId: String? = null
    var cityId: String? = null
}
