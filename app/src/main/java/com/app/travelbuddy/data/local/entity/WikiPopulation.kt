package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

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