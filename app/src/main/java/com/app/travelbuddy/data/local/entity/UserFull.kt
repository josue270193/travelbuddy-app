package com.app.travelbuddy.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserFull(
    @Embedded val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
    )
    val favorites: List<UserFavorite>
) {
}