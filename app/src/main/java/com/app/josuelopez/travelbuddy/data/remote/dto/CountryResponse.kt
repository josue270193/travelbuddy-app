package com.app.josuelopez.travelbuddy.data.remote.dto

import androidx.annotation.Keep
import java.util.*

@Keep
data class CountryResponse(
    val country: CountryDetail,
    val cities: List<CityDetail>,
)

@Keep
data class CityDetail(
    val id: String,
    val name: String,
    val wiki: WikiDetailDto? = null,
    val rating_average: Number
)

@Keep
data class CountryDetail(
    val name: String,
    val wiki: WikiDetailDto? = null
)

@Keep
data class WikiDetailDto(
    val alias: String? = null,
    val labels: String? = null,
    val descriptions: String? = null,
    val images: DataImage? = null,
    val banner: DataImage? = null,
    val population: DataPopulation? = null,
    val geolocation: DataLocation? = null,
    val webpage: DataWebPage? = null,
    val demonym: DataDemonym? = null,
    val currency: DataCurrency? = null,
    val language: DataLanguage? = null,
    val emoji: String? = null
)

@Keep
data class DataLanguage(
    val description: String? = null,
    val value: String? = null
)

@Keep
data class DataCurrency(
    val description: String? = null,
    val value: String? = null,
    val symbol: String? = null
)

@Keep
class DataDemonym(
    val description: String? = null,
    val value: String? = null
)

@Keep
data class DataWebPage(
    val description: String? = null,
    val value: List<String>? = listOf()
)

@Keep
data class DataLocation(
    val description: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val precision: Double? = null
)


@Keep
data class DataPopulation(
    val description: String? = null,
    val value: String? = null,
    val date: Date? = null
)

@Keep
data class DataImage(
    val description: String? = null,
    val value: List<String>? = listOf()
)






















