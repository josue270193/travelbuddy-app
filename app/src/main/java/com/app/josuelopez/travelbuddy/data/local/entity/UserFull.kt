package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation

@Keep
data class UserFull(
    @Embedded val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
    )
    val favorites: List<UserFavorite>
) {
}