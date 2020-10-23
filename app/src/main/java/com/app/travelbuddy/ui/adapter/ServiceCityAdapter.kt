package com.app.travelbuddy.ui.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.ui.interfaces.CardServiceListener
import com.app.travelbuddy.ui.model.ServiceCityModel
import com.app.travelbuddy.utils.StringUtil
import com.app.travelbuddy.utils.StringUtil.capitalizeWords
import com.google.android.material.card.MaterialCardView

class ServiceCityAdapter(
    private var serviceList: List<ServiceCityModel>,
    private var serviceListener: CardServiceListener
) :
    RecyclerView.Adapter<ServiceCityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_service_city, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val resources: Resources = viewHolder.itemView.context.resources
        viewHolder.bindItem(serviceList[position], resources)
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    fun update(newList: List<ServiceCityModel>) {
        serviceList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemCard: MaterialCardView = itemView.findViewById(R.id.service_city_card)
        var itemTitle: TextView = itemView.findViewById(R.id.service_city_title)
        var itemReviewAmount: TextView = itemView.findViewById(R.id.service_city_reviews)
        var itemImage: ImageView = itemView.findViewById(R.id.service_city_image)
        var itemRanking: RatingBar = itemView.findViewById(R.id.service_city_ranking)

        fun bindItem(item: ServiceCityModel, resources: Resources) {
            val transitionName = StringUtil.toSnakeCase(item.title)
            itemCard.transitionName =
                resources.getString(R.string.serviceCardTransitionName, transitionName)
            itemTitle.text = item.title.capitalizeWords()
            itemReviewAmount.text =
                resources.getString(R.string.serviceCardReviewAmount, item.reviewAmount)
            itemRanking.rating = item.ranking.toFloat()
            item.image?.let { itemImage.setImageResource(it) }
            itemCard.setOnClickListener(serviceListener.onClickCard(item, itemCard))
        }
    }
}