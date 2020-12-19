package com.app.josuelopez.travelbuddy.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.josuelopez.travelbuddy.data.local.entity.CountryFull
import com.app.josuelopez.travelbuddy.data.repository.CountryRepository
import com.app.josuelopez.travelbuddy.utils.Resource

class CountryViewModel @ViewModelInject constructor(private val countryRepository: CountryRepository) :
    ViewModel() {

    fun getWithCitiesByCountry(name: String): LiveData<Resource<CountryFull>> {
        return countryRepository.getWithCitiesByCountryAndRanking(name)
    }

    fun getCountryByIp(): LiveData<Resource<String>> {
        return countryRepository.getIpInformation()
    }
}