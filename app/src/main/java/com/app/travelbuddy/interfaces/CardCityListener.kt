package com.app.travelbuddy.interfaces

import android.view.View
import com.app.travelbuddy.domain.City

interface CardCityListener {
    fun onClickCity(cityData: City): View.OnClickListener
}