package com.app.travelbuddy.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.domain.TipModel
import com.google.android.material.card.MaterialCardView

class TipCountryAdapter(private var userList: List<TipModel>) :
    RecyclerView.Adapter<TipCountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tips_country, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemTitle.text = userList[position].title
        viewHolder.itemDescription.text = userList[position].description
        if (userList[position].color != null) {
            viewHolder.itemCard.setBackgroundColor(Color.parseColor(userList[position].color))
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun update(newList: List<TipModel>) {
        userList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.tip_country_title)
        var itemDescription: TextView = itemView.findViewById(R.id.tip_country_description)
        var itemCard: MaterialCardView = itemView.findViewById(R.id.tip_country_card)

    }
}