package com.app.travelbuddy.service

import com.app.travelbuddy.domain.NplRequest
import com.app.travelbuddy.domain.NplResponse
import com.app.travelbuddy.util.WebClientUtil
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface NplService {

    @POST("api/npl/sentimentAnalysis")
    fun postSentimentAnalysis(@Body request: NplRequest): Observable<NplResponse>

    companion object {
        fun create(): NplService {
            return WebClientUtil.client().create(NplService::class.java)
        }
    }
}
