package com.app.travelbuddy.ui.interfaces

import android.view.View
import com.app.travelbuddy.ui.model.FavoriteCityModel
import com.google.android.material.card.MaterialCardView

interface CardFavoriteListener {
    fun onClickCardFavorite(
        favoriteModel: FavoriteCityModel,
        cardView: MaterialCardView
    ): View.OnClickListener
}