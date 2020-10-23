package com.app.travelbuddy.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.travelbuddy.data.local.entity.CountryFull
import com.app.travelbuddy.data.repository.CountryRepository
import com.app.travelbuddy.utils.Resource

class CountryViewModel @ViewModelInject constructor(private val countryRepository: CountryRepository) :
    ViewModel() {

    fun getWithCitiesByCountry(name: String): LiveData<Resource<CountryFull>> {
        return countryRepository.getWithCitiesByCountryAndRanking(name)
    }

    fun getCountryByIp(): LiveData<Resource<String>> {
        return countryRepository.getIpInformation()
    }
}