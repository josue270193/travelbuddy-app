package com.app.josuelopez.travelbuddy.ui.interfaces

import android.view.View
import com.app.josuelopez.travelbuddy.ui.model.CityModel
import com.google.android.material.card.MaterialCardView

interface CardCityListener {
    fun onClickCardRemainCity(city: CityModel, cardView: MaterialCardView): View.OnClickListener
}