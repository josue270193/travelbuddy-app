package com.app.josuelopez.travelbuddy.ui.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.josuelopez.travelbuddy.R
import com.app.josuelopez.travelbuddy.ui.interfaces.CardAttractionListener
import com.app.josuelopez.travelbuddy.ui.model.AttractionCityModel
import com.app.josuelopez.travelbuddy.utils.StringUtil
import com.app.josuelopez.travelbuddy.utils.StringUtil.capitalizeWords
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.card.MaterialCardView

class AttractionCityAdapter(
    private var attractionList: List<AttractionCityModel>,
    private var attractionListener: CardAttractionListener
) :
    RecyclerView.Adapter<AttractionCityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_attraction_city, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val resources: Resources = viewHolder.itemView.context.resources
        viewHolder.bindItem(attractionList[position], resources)
    }

    override fun getItemCount(): Int {
        return attractionList.size
    }

    fun update(newList: List<AttractionCityModel>) {
        attractionList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemCard: MaterialCardView = itemView.findViewById(R.id.attraction_city_card)
        var itemTitle: TextView = itemView.findViewById(R.id.attraction_city_title)
        var itemImage: ImageView = itemView.findViewById(R.id.attraction_city_image)

        fun bindItem(item: AttractionCityModel, resources: Resources) {
            val transitionName = StringUtil.toSnakeCase(item.title)
            itemCard.transitionName =
                resources.getString(R.string.attractionCardTransitionName, transitionName)
            itemTitle.text = item.title.capitalizeWords()

            item.imageUrl?.let {
                Glide.with(itemImage.context).load(it)
                    .transition(withCrossFade())
                    .into(itemImage)
            }
            itemCard.setOnClickListener(attractionListener.onClickCardAttraction(item, itemCard))
        }
    }
}