package com.app.travelbuddy.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.liveData
import com.app.travelbuddy.data.local.SharedPreferenceStringLiveData
import com.app.travelbuddy.data.local.dao.CountryDao
import com.app.travelbuddy.data.remote.CountryRemoteDataSource
import com.app.travelbuddy.data.remote.dto.enumeration.TypeCitySort
import com.app.travelbuddy.utils.ConstantUtil
import com.app.travelbuddy.utils.Resource
import com.app.travelbuddy.utils.networkBoundResource
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val remoteDataSource: CountryRemoteDataSource,
    private val localDataSource: CountryDao,
    private val sharedPreferences: SharedPreferences
) {

    fun getWithCitiesByCountryAndRanking(name: String) = networkBoundResource(
        fetchFromLocal = {
            localDataSource.getWithCitiesByName(name)
        },
        shouldFetchFromRemote = {
            it == null
        },
        fetchFromRemote = {
            remoteDataSource.getByCountryAndSort(name, TypeCitySort.RANKING)
        },
        processRemoteResponse = { },
        saveRemoteData = {
            localDataSource.insertCountryFull(it)
        },
        onFetchFailed = { _, _ -> }
    )

    fun getIpInformation() = networkBoundResource(
        fetchFromLocal = {
            SharedPreferenceStringLiveData(
                sharedPreferences,
                ConstantUtil.PREFERENCE_VARIABLE_COUNTRY,
                ""
            )
        },
        shouldFetchFromRemote = {
            it.isNullOrBlank()
        },
        fetchFromRemote = {
            remoteDataSource.getIpInformation()
        },
        processRemoteResponse = { },
        saveRemoteData = {
            sharedPreferences.edit(
                true
            ) {
                putString(
                    ConstantUtil.PREFERENCE_VARIABLE_COUNTRY,
                    it.country.toLowerCase(Locale.getDefault())
                )
            }
        },
        onFetchFailed = { _, _ -> }
    )

    fun getDetailByCountryAndCity(country: String, city: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        emit(remoteDataSource.getDetailByCountryAndCity(country, city))
    }
}