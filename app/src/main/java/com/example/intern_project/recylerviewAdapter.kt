package com.example.intern_project

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intern_project.dataclasses.citiesDataClasses.cities_data_classes
import com.example.intern_project.dataclasses.last_restaurant.last_restaurant_data_class
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.view.*

class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

public interface onClick{
    fun onClick(id: String,name:String)
}

class myAdapter(val list : last_restaurant_data_class?,val context: Context) : RecyclerView.Adapter<viewHolder>(){


    var clickInterface : onClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = li.inflate(R.layout.item_view,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount() = list?.restaurants!!.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Picasso.get().load(list!!.restaurants[position].restaurant.photos[0].photo.url).transform(CircleTransform()).into(holder.itemView.item_view_image_View);
        holder.itemView.item_view_text_view.text = list!!.restaurants[position].restaurant.name
        holder.itemView.setOnClickListener {
            clickInterface?.onClick(list!!.restaurants[position].restaurant.id,list!!.restaurants[position].restaurant.name)
        }
    }


}
