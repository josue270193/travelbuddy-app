package com.app.josuelopez.travelbuddy.ui.interfaces

import android.view.View
import com.app.josuelopez.travelbuddy.ui.model.AttractionCityModel
import com.google.android.material.card.MaterialCardView

interface CardAttractionListener {
    fun onClickCardAttraction(attractionModel: AttractionCityModel, cardView: MaterialCardView): View.OnClickListener
}