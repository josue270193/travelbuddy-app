package com.app.travelbuddy.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.ui.model.TipsServiceModel

class TipsServiceAdapter(private var tipsList: List<TipsServiceModel>) :
    RecyclerView.Adapter<TipsServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tips_service, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context
        viewHolder.bindItem(tipsList[position], context)
    }

    override fun getItemCount(): Int {
        return tipsList.size
    }

    fun update(newList: List<TipsServiceModel>) {
        tipsList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemText: TextView = itemView.findViewById(R.id.item_tips_service_value)

        fun bindItem(item: TipsServiceModel, context: Context?) {
            itemText.text = item.value
        }
    }
}