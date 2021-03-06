package com.app.josuelopez.travelbuddy.data.remote.service

import com.app.josuelopez.travelbuddy.data.remote.dto.IpInformationResponse
import retrofit2.Response
import retrofit2.http.GET

interface IpApiService {

    @GET("http://ip-api.com/json")
    suspend fun getIpInformation(): Response<IpInformationResponse>
}