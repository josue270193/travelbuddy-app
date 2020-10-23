package com.app.travelbuddy.data.remote.dto

import java.util.*

data class CountryResponse(
    val country: CountryDetail,
    val cities: List<CityResponse>,
)

data class CityResponse(
    val _id: String,
    val city: CityDetail,
    val rating_average: Number
)

data class CountryDetail(
    val name: String,
    val wiki: WikiDetailDto? = null
)

data class CityDetail(
    val name: String,
    val wiki: WikiDetailDto? = null
)

data class WikiDetailDto(
    val alias: String? = null,
    val labels: String? = null,
    val descriptions: String? = null,
    val images: DataImage? = null,
    val population: DataPopulation? = null,
    val geolocation: DataLocation? = null,
    val webpage: DataWebPage? = null,
    val demonym: DataDemonym? = null,
    val currency: DataCurrency? = null,
    val language: DataLanguage? = null,
    val emoji: String? = null
)

data class DataLanguage(
    val description: String? = null,
    val value: String? = null
)

data class DataCurrency(
    val description: String? = null,
    val value: String? = null,
    val symbol: String? = null
)

class DataDemonym(
    val description: String? = null,
    val value: String? = null
)


data class DataWebPage(
    val description: String? = null,
    val value: List<String>? = listOf()
)


data class DataLocation(
    val description: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val precision: Double? = null
)


data class DataPopulation(
    val description: String? = null,
    val value: String? = null,
    val date: Date? = null
)

data class DataImage(
    val description: String? = null,
    val value: List<String>? = listOf()
)






















