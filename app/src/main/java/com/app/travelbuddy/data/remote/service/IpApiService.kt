package com.app.travelbuddy.data.remote.service

import com.app.travelbuddy.data.remote.dto.IpInformationResponse
import retrofit2.Response
import retrofit2.http.GET

interface IpApiService {

    @GET("http://ip-api.com/json")
    suspend fun getIpInformation(): Response<IpInformationResponse>
}