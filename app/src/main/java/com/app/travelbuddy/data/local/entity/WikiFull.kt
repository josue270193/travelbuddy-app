package com.app.travelbuddy.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WikiFull(
    @Embedded val wiki: Wiki
) {

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiId",
        entity = WikiImage::class
    )
    var wikiImage: WikiImageFull? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiId"
    )
    var wikiPopulation: WikiPopulation? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiId"
    )
    var wikiGeolocation: WikiGeolocation? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiId",
        entity = WikiWebPage::class
    )
    var wikiWebPage: WikiWebPageFull? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiId"
    )
    var wikiDemonym: WikiDemonym? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiId"
    )
    var wikiCurrency: WikiCurrency? = null

    @Relation(
        parentColumn = "id",
        entityColumn = "wikiId"
    )
    var wikiLanguage: WikiLanguage? = null
}
