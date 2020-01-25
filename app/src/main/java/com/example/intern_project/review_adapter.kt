package com.example.intern_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intern_project.dataclasses.last_restaurant.last_restaurant_data_class
import com.example.intern_project.dataclasses.review_data_classes.review_data_class
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.android.synthetic.main.review_item_view.view.*
import kotlin.math.roundToInt


class reviewAdapter(val list : review_data_class?, val context: Context) : RecyclerView.Adapter<viewHolder>(){


    var clickInterface : onClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = li.inflate(R.layout.review_item_view,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount() = list?.user_reviews!!.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.itemView.reviewTextView.text = list!!.user_reviews[position].review.user.name
        val rating = list?.user_reviews[position].review.rating.roundToInt()
        if(rating==1){
            holder.itemView.star1.setImageResource(R.drawable.star)
        }
        if(rating==2){
            holder.itemView.star1.setImageResource(R.drawable.star)
            holder.itemView.star2.setImageResource(R.drawable.star)
        }
        if(rating==3){
            holder.itemView.star1.setImageResource(R.drawable.star)
            holder.itemView.star2.setImageResource(R.drawable.star)
            holder.itemView.star3.setImageResource(R.drawable.star)

        }
        if(rating==4){

            holder.itemView.star1.setImageResource(R.drawable.star)
            holder.itemView.star2.setImageResource(R.drawable.star)
            holder.itemView.star3.setImageResource(R.drawable.star)
            holder.itemView.star4.setImageResource(R.drawable.star)
        }
        if(rating==5){
            holder.itemView.star1.setImageResource(R.drawable.star)
            holder.itemView.star2.setImageResource(R.drawable.star)
            holder.itemView.star3.setImageResource(R.drawable.star)
            holder.itemView.star4.setImageResource(R.drawable.star)
            holder.itemView.star5.setImageResource(R.drawable.star)
        }
    }


}
