package com.app.travelbuddy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wiki_currency")
data class WikiCurrency(
    val description: String? = null,
    val value: String? = null,
    val symbol: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var wikiId: Long? = null
}