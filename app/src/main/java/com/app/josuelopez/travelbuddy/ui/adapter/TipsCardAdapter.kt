package com.app.josuelopez.travelbuddy.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.josuelopez.travelbuddy.R
import com.app.josuelopez.travelbuddy.ui.interfaces.CardTipsListener
import com.app.josuelopez.travelbuddy.ui.model.TipModel
import com.app.josuelopez.travelbuddy.ui.model.enumeration.TypeTipModel
import com.google.android.material.card.MaterialCardView


class TipsCardAdapter(
    private var userList: List<TipModel>,
    private var tipsListener: CardTipsListener,
    val limitItems: Int
) :
    RecyclerView.Adapter<TipsCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tips_country, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context
        viewHolder.bindItem(userList[position], context)
    }

    override fun getItemCount(): Int {
        if (userList.size > limitItems) {
            return limitItems
        }
        return userList.size
    }

    fun update(newList: List<TipModel>) {
        userList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemDescription: TextView = itemView.findViewById(R.id.tips_description)
        var itemValue: TextView = itemView.findViewById(R.id.tips_value)
        var itemIcon: AppCompatImageView = itemView.findViewById(R.id.tips_icon)
        var itemCard: MaterialCardView = itemView.findViewById(R.id.tips_card)

        @SuppressLint("ResourceType")
        fun bindItem(tipModel: TipModel, context: Context) {
            itemDescription.text = tipModel.description
            itemValue.text = tipModel.value
            itemCard.setOnClickListener(tipsListener.onClickCardTips(tipModel, itemCard))

            when (tipModel.type) {
                TypeTipModel.CURRENCY -> {
                    itemDescription.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemValue.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemIcon.setTint(R.color.colorTextDark)
                    itemIcon.setImageResource(R.drawable.ic_icon_currency)
                    itemCard.setBackgroundColor(Color.parseColor(context.getString(R.color.colorTipsCurrency)))
                }
                TypeTipModel.EMOJI -> {
                    itemDescription.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemValue.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemIcon.setTint(R.color.colorTextDark)
                    itemIcon.setImageResource(R.drawable.ic_logo)
                    itemCard.setBackgroundColor(Color.parseColor(context.getString(R.color.colorTipsEmoji)))
                }
                TypeTipModel.WEBPAGE -> {
                    itemDescription.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemValue.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemValue.text = context.resources.getString(R.string.valueTipsWebpage)
                    itemIcon.setTint(R.color.colorTextDark)
                    itemIcon.setImageResource(R.drawable.ic_icon_webpage)
                    itemCard.setBackgroundColor(Color.parseColor(context.getString(R.color.colorTipsWebpage)))
                }
                TypeTipModel.POPULATION -> {
                    itemDescription.setTextColor(Color.parseColor(context.getString(R.color.colorText)))
                    itemValue.setTextColor(Color.parseColor(context.getString(R.color.colorText)))
                    itemIcon.setTint(R.color.colorText)
                    itemIcon.setImageResource(R.drawable.ic_icon_population)
                    itemCard.setBackgroundColor(Color.parseColor(context.getString(R.color.colorTipsPopulation)))
                }
                TypeTipModel.GEOLOCATION -> {
                    itemDescription.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemValue.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemValue.text = context.resources.getString(R.string.valueTipsGeolocation)
                    itemIcon.setTint(R.color.colorTextDark)
                    itemIcon.setImageResource(R.drawable.ic_icon_geolocation)
                    itemCard.setBackgroundColor(Color.parseColor(context.getString(R.color.colorTipsGeolocation)))
                }
                TypeTipModel.DEMONYM -> {
                    itemDescription.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemValue.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemIcon.setTint(R.color.colorTextDark)
                    itemIcon.setImageResource(R.drawable.ic_icon_demonym)
                    itemCard.setBackgroundColor(Color.parseColor(context.getString(R.color.colorTipsDemonym)))
                }
                TypeTipModel.LANGUAGE -> {
                    itemDescription.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemValue.setTextColor(Color.parseColor(context.getString(R.color.colorTextDark)))
                    itemIcon.setTint(R.color.colorTextDark)
                    itemIcon.setImageResource(R.drawable.ic_icon_demonym)
                    itemCard.setBackgroundColor(Color.parseColor(context.getString(R.color.colorTipsLanguage)))
                }
            }
        }

        fun ImageView.setTint(@ColorRes colorRes: Int) {
            ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
        }
    }
}