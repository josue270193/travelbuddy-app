package com.app.travelbuddy.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.travelbuddy.data.remote.dto.CityDetailResponse
import com.app.travelbuddy.data.repository.CountryRepository
import com.app.travelbuddy.utils.Resource

class CityViewModel @ViewModelInject constructor(private val countryRepository: CountryRepository) :
    ViewModel() {

    fun getDetailByCountryAndCity(
        country: String,
        city: String
    ): LiveData<Resource<List<CityDetailResponse>>> {
        return countryRepository.getDetailByCountryAndCity(country, city)
    }
}