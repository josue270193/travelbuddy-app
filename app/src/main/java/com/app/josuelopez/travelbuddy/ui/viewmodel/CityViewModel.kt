package com.app.josuelopez.travelbuddy.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.josuelopez.travelbuddy.data.remote.dto.CityDetailResponse
import com.app.josuelopez.travelbuddy.data.repository.CountryRepository
import com.app.josuelopez.travelbuddy.utils.Resource

class CityViewModel @ViewModelInject constructor(private val countryRepository: CountryRepository) :
    ViewModel() {

    fun getDetailByCountryAndCity(
        country: String,
        city: String
    ): LiveData<Resource<List<CityDetailResponse>>> {
        return countryRepository.getDetailByCountryAndCity(country, city)
    }
}