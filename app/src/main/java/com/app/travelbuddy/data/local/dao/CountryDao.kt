package com.app.travelbuddy.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.travelbuddy.data.local.entity.*
import com.app.travelbuddy.data.remote.dto.CityDetail
import com.app.travelbuddy.data.remote.dto.CountryResponse

@Dao
interface CountryDao {

    @Transaction
    @Query("SELECT * FROM country c WHERE c.name = :name")
    fun getWithCitiesByName(name: String): LiveData<CountryFull>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: Country): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: List<City>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wiki: Wiki): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wikiImage: WikiImage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWikiImageDetail(wikiImageDetail: List<WikiImageDetail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wikiCurrency: WikiCurrency): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wikiDemonym: WikiDemonym): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wikiGeolocation: WikiGeolocation): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wikiPopulation: WikiPopulation): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wikiLanguage: WikiLanguage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wikiWebPage: WikiWebPage): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWikiWebPageDetail(wikiWebPageDetail: List<WikiWebPageDetail>)

    @Transaction
    fun insertCitiesFull(cities: List<CityDetail>, countryId: Long) {
        cities.forEach {
            val cityId = it.id
            val city = City(cityId, it.name, it.rating_average.toDouble())
            city.countryId = countryId
            it.wiki?.banner?.value?.let { cityWikiImages ->
                city.imageUrl = cityWikiImages.getOrNull(0)
            }
            it.wiki?.images?.value?.let { cityWikiImages ->
                city.imageUrl = cityWikiImages.getOrNull(0)
            }
            insert(city)

            it.wiki?.let { wikiDetail ->
                val wiki = Wiki(
                    wikiDetail.alias,
                    wikiDetail.labels,
                    wikiDetail.descriptions,
                    wikiDetail.emoji
                )
                wiki.cityId = cityId
                val wikiId = insert(wiki)

                wikiDetail.images?.let { dataImage ->
                    val wikiImage = WikiImage(dataImage.description)
                    wikiImage.wikiId = wikiId
                    val wikiImageId = insert(wikiImage)

                    dataImage.value?.let { dataImageDetail ->
                        val dataImageDetails = dataImageDetail.map {
                            val wikiImageDetail = WikiImageDetail(it)
                            wikiImageDetail.wikiImageId = wikiImageId
                            wikiImageDetail
                        }
                        insertWikiImageDetail(dataImageDetails)
                    }
                }
                wikiDetail.currency?.let { dataCurrency ->
                    val wikiCurrency =
                        WikiCurrency(
                            dataCurrency.description,
                            dataCurrency.value,
                            dataCurrency.symbol
                        )
                    wikiCurrency.wikiId = wikiId
                    insert(wikiCurrency)
                }
                wikiDetail.demonym?.let { dataDemonym ->
                    val wikiDemonym = WikiDemonym(dataDemonym.description, dataDemonym.value)
                    wikiDemonym.wikiId = wikiId
                    insert(wikiDemonym)
                }
                wikiDetail.geolocation?.let { dataLocation ->
                    val wikiGeolocation = WikiGeolocation(
                        dataLocation.description,
                        dataLocation.latitude,
                        dataLocation.longitude,
                        dataLocation.precision
                    )
                    wikiGeolocation.wikiId = wikiId
                    insert(wikiGeolocation)
                }
                wikiDetail.population?.let { dataPopulation ->
                    val wikiPopulation = WikiPopulation(
                        dataPopulation.description,
                        dataPopulation.value,
                        dataPopulation.date
                    )
                    wikiPopulation.wikiId = wikiId
                    insert(wikiPopulation)
                }
                wikiDetail.language?.let { dataLanguage ->
                    val wikiLanguage = WikiLanguage(dataLanguage.description, dataLanguage.value)
                    wikiLanguage.wikiId = wikiId
                    insert(wikiLanguage)
                }
                wikiDetail.webpage?.let { dataWebPage ->
                    val wikiWebPage = WikiWebPage(dataWebPage.description)
                    wikiWebPage.wikiId = wikiId
                    val wikiWebPageId = insert(wikiWebPage)

                    dataWebPage.value?.let { dataWebPageDetail ->
                        val dataWebPageDetails = dataWebPageDetail.map {
                            val wikiWebPageDetail = WikiWebPageDetail(it)
                            wikiWebPageDetail.wikiWebPageId = wikiWebPageId
                            wikiWebPageDetail
                        }
                        insertWikiWebPageDetail(dataWebPageDetails)
                    }
                }
            }
        }
    }

    @Transaction
    fun insertCountryFull(response: CountryResponse) {
        val country = Country(response.country.name)
        val countryId = insert(country)

        insertCitiesFull(response.cities, countryId)

        response.country.wiki?.let { wikiDetail ->
            val wiki = Wiki(
                wikiDetail.alias,
                wikiDetail.labels,
                wikiDetail.descriptions,
                wikiDetail.emoji
            )
            wiki.countryId = countryId
            val wikiId = insert(wiki)

            wikiDetail.images?.let { dataImage ->
                val wikiImage = WikiImage(dataImage.description)
                wikiImage.wikiId = wikiId
                val wikiImageId = insert(wikiImage)

                dataImage.value?.let { dataImageDetail ->
                    val dataImageDetails = dataImageDetail.map {
                        val wikiImageDetail = WikiImageDetail(it)
                        wikiImageDetail.wikiImageId = wikiImageId
                        wikiImageDetail
                    }
                    insertWikiImageDetail(dataImageDetails)
                }
            }
            wikiDetail.currency?.let { dataCurrency ->
                val wikiCurrency =
                    WikiCurrency(dataCurrency.description, dataCurrency.value, dataCurrency.symbol)
                wikiCurrency.wikiId = wikiId
                insert(wikiCurrency)
            }
            wikiDetail.demonym?.let { dataDemonym ->
                val wikiDemonym = WikiDemonym(dataDemonym.description, dataDemonym.value)
                wikiDemonym.wikiId = wikiId
                insert(wikiDemonym)
            }
            wikiDetail.geolocation?.let { dataLocation ->
                val wikiGeolocation = WikiGeolocation(
                    dataLocation.description,
                    dataLocation.latitude,
                    dataLocation.longitude,
                    dataLocation.precision
                )
                wikiGeolocation.wikiId = wikiId
                insert(wikiGeolocation)
            }
            wikiDetail.population?.let { dataPopulation ->
                val wikiPopulation = WikiPopulation(
                    dataPopulation.description,
                    dataPopulation.value,
                    dataPopulation.date
                )
                wikiPopulation.wikiId = wikiId
                insert(wikiPopulation)
            }
            wikiDetail.language?.let { dataLanguage ->
                val wikiLanguage = WikiLanguage(dataLanguage.description, dataLanguage.value)
                wikiLanguage.wikiId = wikiId
                insert(wikiLanguage)
            }
            wikiDetail.webpage?.let { dataWebPage ->
                val wikiWebPage = WikiWebPage(dataWebPage.description)
                wikiWebPage.wikiId = wikiId
                val wikiWebPageId = insert(wikiWebPage)

                dataWebPage.value?.let { dataWebPageDetail ->
                    val dataWebPageDetails = dataWebPageDetail.map {
                        val wikiWebPageDetail = WikiWebPageDetail(it)
                        wikiWebPageDetail.wikiWebPageId = wikiWebPageId
                        wikiWebPageDetail
                    }
                    insertWikiWebPageDetail(dataWebPageDetails)
                }
            }
        }
    }

    @Query("DELETE FROM country")
    fun deleteAll()
}