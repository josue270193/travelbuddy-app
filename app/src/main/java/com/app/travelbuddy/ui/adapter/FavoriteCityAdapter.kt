package com.app.travelbuddy.ui.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.ui.interfaces.CardFavoriteListener
import com.app.travelbuddy.ui.model.FavoriteCityModel
import com.app.travelbuddy.utils.StringUtil
import com.app.travelbuddy.utils.StringUtil.capitalizeWords
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.card.MaterialCardView

class FavoriteCityAdapter(
    private var favoriteList: List<FavoriteCityModel>,
    private var favoriteListener: CardFavoriteListener
) :
    RecyclerView.Adapter<FavoriteCityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite_city, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val resources: Resources = viewHolder.itemView.context.resources
        viewHolder.bindItem(favoriteList[position], resources)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    fun update(newList: List<FavoriteCityModel>) {
        favoriteList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemCard: MaterialCardView = itemView.findViewById(R.id.favorite_city_card)
        var itemTitle: TextView = itemView.findViewById(R.id.favorite_city_image)
        var itemImage: ImageView = itemView.findViewById(R.id.favorite_city_image)

        fun bindItem(item: FavoriteCityModel, resources: Resources) {
            val transitionName = StringUtil.toSnakeCase(item.title)
            itemCard.transitionName =
                resources.getString(R.string.attractionCardTransitionName, transitionName)
            itemTitle.text = item.title.capitalizeWords()

            item.imageUrl?.let {
                Glide.with(itemImage.context).load(it)
                    .transition(withCrossFade())
                    .into(itemImage)
            }
            itemCard.setOnClickListener(favoriteListener.onClickCardFavorite(item, itemCard))
        }
    }
}