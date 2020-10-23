package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wiki_webpage_detail")
data class WikiWebPageDetail(
    val value: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var wikiWebPageId: Long? = null
}