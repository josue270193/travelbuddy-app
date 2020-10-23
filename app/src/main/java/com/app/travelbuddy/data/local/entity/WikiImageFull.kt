package com.app.travelbuddy.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WikiImageFull(
    @Embedded val wikiImage: WikiImage
) {

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiImageId"
    )
    var wikiImageDetails: List<WikiImageDetail>? = null
}
