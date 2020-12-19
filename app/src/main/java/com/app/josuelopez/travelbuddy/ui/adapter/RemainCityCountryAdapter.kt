package com.app.josuelopez.travelbuddy.ui.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.josuelopez.travelbuddy.R
import com.app.josuelopez.travelbuddy.ui.interfaces.CardCityListener
import com.app.josuelopez.travelbuddy.ui.model.CityModel
import com.app.josuelopez.travelbuddy.utils.StringUtil
import com.app.josuelopez.travelbuddy.utils.StringUtil.capitalizeWords
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.card.MaterialCardView
import java.text.DecimalFormat

class RemainCityCountryAdapter(
    private var userList: List<CityModel>,
    private var cardListenerListener: CardCityListener
) :
    RecyclerView.Adapter<RemainCityCountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_remain_city_country, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val resources: Resources = viewHolder.itemView.context.resources
        viewHolder.bindItem(userList[position], resources)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun update(newList: List<CityModel>) {
        userList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemCard: MaterialCardView = itemView.findViewById(R.id.remain_city_country_card)
        var itemTitle: TextView = itemView.findViewById(R.id.remain_city_country_title)
        var itemRanking: TextView = itemView.findViewById(R.id.remain_city_country_ranking)
        var itemImage: ImageView = itemView.findViewById(R.id.remain_city_country_image)

        fun bindItem(item: CityModel, resources: Resources) {
            val transitionName = StringUtil.toSnakeCase(item.city)
            val df = DecimalFormat("#.##")
            itemCard.transitionName =
                resources.getString(R.string.cityCardTransitionName, transitionName)
            itemTitle.text = item.city.capitalizeWords()
            itemRanking.text =
                resources.getString(R.string.descriptionRankingCity, df.format(item.ranking))
            item.imageUrl?.let {
                Glide.with(itemImage.context).load(it)
                    .transition(withCrossFade())
                    .into(itemImage)
            }
            itemCard.setOnClickListener(cardListenerListener.onClickCardRemainCity(item, itemCard))
        }
    }
}