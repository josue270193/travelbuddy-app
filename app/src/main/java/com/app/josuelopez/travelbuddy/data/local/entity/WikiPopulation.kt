package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Keep
@Entity(tableName = "wiki_population")
data class WikiPopulation(
    val description: String? = null,
    val value: String? = null,
    val date: Date? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var wikiId: Long? = null
}