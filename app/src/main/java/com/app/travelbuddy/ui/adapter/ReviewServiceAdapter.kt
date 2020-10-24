package com.app.travelbuddy.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.travelbuddy.R
import com.app.travelbuddy.ui.model.ReviewServiceModel
import com.google.android.material.card.MaterialCardView

class ReviewServiceAdapter(private var reviewList: List<ReviewServiceModel>) :
    RecyclerView.Adapter<ReviewServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_review_service, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = reviewList[position]
        viewHolder.itemText.text = item.review
        item.date?.let {
            viewHolder.itemDate.text = it.toString()
        }

        var imageId = R.drawable.shape_gradient_review_bad
        if (item.ranking >= VALUE_GOOD) {
            imageId = R.drawable.shape_gradient_review_good
        }
        viewHolder.itemImage.setImageResource(imageId)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    fun update(newList: List<ReviewServiceModel>) {
        reviewList = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemText: TextView = itemView.findViewById(R.id.review_service_text)
        var itemDate: TextView = itemView.findViewById(R.id.review_service_date)
        var itemImage: ImageView = itemView.findViewById(R.id.review_service_image)
        var itemCard: MaterialCardView = itemView.findViewById(R.id.review_service_card)

    }

    companion object {
        const val VALUE_GOOD = 3.0
    }
}