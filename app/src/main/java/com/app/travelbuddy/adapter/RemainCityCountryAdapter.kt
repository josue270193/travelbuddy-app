package com.app.travelbuddy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.domain.RemainCityModel
import com.app.travelbuddy.interfaces.CardCityListener
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import java.text.DecimalFormat

class RemainCityCountryAdapter(
    private var userList: List<RemainCityModel>,
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
        val df = DecimalFormat("#.##")
        viewHolder.itemTitle.text = userList[position].title
        viewHolder.itemRanking.text = df.format(userList[position].ranking)
        viewHolder.itemCard.setOnClickListener(cardListenerListener.onClickCity(userList[position].data))
        userList[position].image?.let {
            Glide.with(viewHolder.itemImage.context).load(it).into(viewHolder.itemImage)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun update(newList: List<RemainCityModel>) {
        userList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.remain_city_country_title)
        var itemRanking: TextView = itemView.findViewById(R.id.remain_city_country_ranking)
        var itemCard: MaterialCardView = itemView.findViewById(R.id.remain_city_country_card)
        var itemImage: ImageView = itemView.findViewById(R.id.remain_city_country_image)

    }
}