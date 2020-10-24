package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wiki_demonym")
data class WikiDemonym(
    val description: String? = null,
    val value: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var wikiId: Long? = null
}