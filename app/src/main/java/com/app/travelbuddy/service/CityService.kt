package com.app.travelbuddy.service

import com.app.travelbuddy.domain.City
import com.app.travelbuddy.util.WebClientUtil
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface CityService {

    @GET("city/list")
    fun getList(): Observable<List<City>>

    companion object {
        fun create(): CityService {
            return WebClientUtil.client().create(CityService::class.java)
        }
    }
}