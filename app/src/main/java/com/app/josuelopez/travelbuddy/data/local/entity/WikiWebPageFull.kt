package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation

@Keep
data class WikiWebPageFull(
    @Embedded val wikiWebPage: WikiWebPage
) {

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiWebPageId"
    )
    var wikiWebPageDetails: List<WikiWebPageDetail>? = null
}
