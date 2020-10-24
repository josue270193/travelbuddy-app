package com.app.travelbuddy.data.remote

import com.app.travelbuddy.data.remote.dto.enumeration.TypeCitySort
import com.app.travelbuddy.data.remote.service.CityService
import com.app.travelbuddy.data.remote.service.IpApiService
import javax.inject.Inject

class CountryRemoteDataSource @Inject constructor(
    private val countryService: CityService,
    private val ipApiService: IpApiService
) : BaseDataSource() {

    suspend fun getByCountryAndSort(country: String, sort: TypeCitySort) =
        getResult { countryService.getByCountryAndSort(country, sort) }


    suspend fun getDetailByCountryAndCity(country: String, city: String) =
        getResult { countryService.getDetailByCountryAndCity(country, city) }


    suspend fun getIpInformation() = getResult {
        ipApiService.getIpInformation()
    }
}