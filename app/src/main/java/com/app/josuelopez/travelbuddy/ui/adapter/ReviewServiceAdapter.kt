package com.app.josuelopez.travelbuddy.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.josuelopez.travelbuddy.R
import com.app.josuelopez.travelbuddy.ui.model.ReviewServiceModel
import com.google.android.material.card.MaterialCardView

class ReviewServiceAdapter(private var reviewList: List<ReviewServiceModel>) :
    RecyclerView.Adapter<ReviewServiceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_review_service, parent, false)
        return ViewHolder(v);
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context
        viewHolder.bindItem(reviewList[position], context)
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
        var itemLayout: LinearLayout = itemView.findViewById(R.id.review_service_layout)

        fun bindItem(item: ReviewServiceModel, context: Context?) {
            itemText.text = item.review
            item.date?.let {
                itemDate.text = it.toString()
            }

            var imageId = R.drawable.ic_icon_good_bad
            var backgroundId = R.drawable.shape_gradient_review_good_bad
            if (item.ranking >= VALUE_GOOD) {
                imageId = R.drawable.ic_icon_good
                backgroundId = R.drawable.shape_gradient_review_good
            } else if (item.ranking <= VALUE_BAD) {
                imageId = R.drawable.ic_icon_bad
                backgroundId = R.drawable.shape_gradient_review_bad
            }
            itemImage.setImageResource(imageId)
            itemLayout.setBackgroundResource(backgroundId)
        }
    }

    companion object {
        const val VALUE_GOOD = 4.0
        const val VALUE_BAD = 2.0
    }
}