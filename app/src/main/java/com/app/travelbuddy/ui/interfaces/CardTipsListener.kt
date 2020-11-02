package com.app.travelbuddy.ui.interfaces

import android.view.View
import com.app.travelbuddy.ui.model.TipModel
import com.google.android.material.card.MaterialCardView

interface CardTipsListener {
    fun onClickCardTips(tipModel: TipModel, cardView: MaterialCardView): View.OnClickListener
}