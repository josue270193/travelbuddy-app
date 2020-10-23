package com.app.travelbuddy.data.remote.service

import com.app.travelbuddy.data.remote.dto.CityDetailResponse
import com.app.travelbuddy.data.remote.dto.CountryResponse
import com.app.travelbuddy.data.remote.dto.enumeration.TypeCitySort
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityService {

    @GET("city/list")
    suspend fun getByCountryAndSort(
        @Query("country") country: String,
        @Query("sort") sort: TypeCitySort
    ): Response<CountryResponse>

    @GET("city/detail")
    suspend fun getDetailByCountryAndCity(
        @Query("country") country: String,
        @Query("city") city: String
    ): Response<CityDetailResponse>
}