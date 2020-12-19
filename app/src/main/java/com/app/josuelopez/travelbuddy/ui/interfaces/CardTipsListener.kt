package com.app.josuelopez.travelbuddy.ui.interfaces

import android.view.View
import com.app.josuelopez.travelbuddy.ui.model.TipModel
import com.google.android.material.card.MaterialCardView

interface CardTipsListener {
    fun onClickCardTips(tipModel: TipModel, cardView: MaterialCardView): View.OnClickListener
}