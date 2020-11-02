package com.app.travelbuddy.ui.interfaces

import android.view.View
import com.app.travelbuddy.ui.model.AttractionCityModel
import com.app.travelbuddy.ui.model.CityModel
import com.google.android.material.card.MaterialCardView

interface CardAttractionListener {
    fun onClickCardAttraction(attractionModel: AttractionCityModel, cardView: MaterialCardView): View.OnClickListener
}