package com.app.travelbuddy.ui.interfaces

import android.view.View
import com.app.travelbuddy.ui.model.ServiceCityModel
import com.google.android.material.card.MaterialCardView

interface CardServiceListener {
    fun onClickCard(
        serviceModel: ServiceCityModel,
        cardView: MaterialCardView
    ): View.OnClickListener
}