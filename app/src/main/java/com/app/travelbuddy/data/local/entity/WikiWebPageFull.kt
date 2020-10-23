package com.app.travelbuddy.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WikiWebPageFull(
    @Embedded val wikiWebPage: WikiWebPage
) {

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiWebPageId"
    )
    var wikiWebPageDetails: List<WikiWebPageDetail>? = null
}
