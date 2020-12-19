package com.app.josuelopez.travelbuddy.utils

import android.content.Context
import com.app.josuelopez.travelbuddy.R
import com.app.josuelopez.travelbuddy.data.local.entity.WikiFull
import com.app.josuelopez.travelbuddy.ui.model.TipModel
import com.app.josuelopez.travelbuddy.ui.model.enumeration.TypeTipModel

internal object WikiUtil {

    fun buildTipsCity(wiki: WikiFull?, context: Context): List<TipModel> {
        return wiki?.let { wikiData ->
            val tips = mutableListOf<TipModel>()
            wikiData.wikiCurrency?.let {
                tips += TipModel(
                    TypeTipModel.CURRENCY,
                    it.description!!,
                    it.value!!
                )
            }
            wikiData.wikiPopulation?.let {
                tips += TipModel(
                    TypeTipModel.POPULATION,
                    it.description!!,
                    it.value!!
                )
            }
            wikiData.wikiDemonym?.let {
                tips += TipModel(
                    TypeTipModel.DEMONYM,
                    it.description!!,
                    it.value!!
                )
            }
            wikiData.wiki.emoji?.let {
                tips += TipModel(
                    TypeTipModel.EMOJI,
                    context.getString(R.string.countryTipEmoji),
                    it
                )
            }
            wikiData.wikiWebPage?.let { it ->
                tips += TipModel(
                    TypeTipModel.WEBPAGE,
                    it.wikiWebPage.description!!,
                    it.wikiWebPageDetails?.map { item -> item.value }?.joinToString()!!
                )
            }
            wikiData.wikiLanguage?.let {
                tips += TipModel(
                    TypeTipModel.LANGUAGE,
                    it.description!!,
                    it.value!!
                )
            }
            wikiData.wikiGeolocation?.let {
                tips += TipModel(
                    TypeTipModel.GEOLOCATION,
                    it.description!!,
                    context.getString(
                        R.string.valueGeolocationMaps,
                        it.latitude.toString(),
                        it.longitude.toString(),
                        it.precision.toString()
                    )
                )
            }
            tips
        }.orEmpty()
    }
}