package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wiki_webpage")
data class WikiWebPage(
    val description: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var wikiId: Long? = null
}