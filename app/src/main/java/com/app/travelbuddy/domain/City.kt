package com.app.travelbuddy.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class City(
    val _id: String,
    val rating_average: Number,
    val city: CityDetail,
    val country: CountryDetail,
    val reviews: List<Reviews>,
    val updatedAt: Date
) : Parcelable {

}

@Parcelize
class Entity(
    val type: String,
    val text: String
) : Parcelable {

}

@Parcelize
class Reviews(
    val text: String,
    val entities: List<Entity>,
    val score: Number
) : Parcelable {

}

@Parcelize
class CityDetail(
    val name: String,
    val wiki: WikiDetail? = null
) : Parcelable {

}

@Parcelize
class WikiDetail(
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
) : Parcelable {

}

@Parcelize
class DataLanguage(
    val description: String? = null,
    val value: String? = null
) : Parcelable {

}

@Parcelize
class DataCurrency : Parcelable {
    val description: String? = null
    val value: String? = null
    val symbol: String? = null
}

@Parcelize
class DataDemonym : Parcelable {
    val description: String? = null
    val value: String? = null
}

@Parcelize
class DataWebPage : Parcelable {
    val description: String? = null
    val value: List<String>? = listOf()
}

@Parcelize
class DataLocation : Parcelable {
    val description: String? = null
    val latitude: Double? = null
    val longitude: Double? = null
    val precision: Double? = null
}

@Parcelize
class DataPopulation(
    val description: String? = null,
    val value: String? = null,
    val date: Date? = null
) : Parcelable {

}

@Parcelize
class DataImage(
    val description: String? = null,
    val value: List<String>? = listOf()
) : Parcelable {

}

@Parcelize
class CountryDetail(
    val name: String,
    val wiki: WikiDetail? = null
) : Parcelable {

}