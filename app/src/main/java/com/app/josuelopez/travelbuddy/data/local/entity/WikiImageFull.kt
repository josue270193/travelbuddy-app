package com.app.josuelopez.travelbuddy.data.local.entity

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Relation

@Keep
data class WikiImageFull(
    @Embedded val wikiImage: WikiImage
) {

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiImageId"
    )
    var wikiImageDetails: List<WikiImageDetail>? = null
}
