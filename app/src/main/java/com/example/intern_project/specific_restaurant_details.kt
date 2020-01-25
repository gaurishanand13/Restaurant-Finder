package com.example.intern_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intern_project.dataclasses.last_restaurant.last_restaurant_data_class
import com.example.intern_project.dataclasses.nearbyRestaurants
import com.example.intern_project.dataclasses.restaurantDetailsById.details
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_specific_restaurant_details.*
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.android.synthetic.main.item_view_specific.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class specific_restaurant_details : AppCompatActivity() {

    fun getRestaurantDetails(id: String){

        val call = RetrofitClient.retrofitService?.restaurantDetailsById(id)
        call?.enqueue(object : Callback<details> {
            override fun onFailure(call: Call<details>, t: Throwable) {
                Log.i("tag",t.message)
                Toast.makeText(this@specific_restaurant_details,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<details>, response: Response<details>) {
                val list = response.body()
                Log.i("print",list.toString())
                progressBarLayout.visibility = View.GONE
                finalLayout.visibility = View.VISIBLE
                specific_recyler_view.layoutManager = LinearLayoutManager(this@specific_restaurant_details,LinearLayoutManager.HORIZONTAL,false)
                if(list?.photos!=null){
                    specific_recyler_view.adapter = specificMyAdapter(list,this@specific_restaurant_details)
                }
                specific_name.text = list?.name
                specific_cusinies.text = list?.cuisines
                specific_address.text = list?.location?.address
                specific_cost.text = "Average cost for two - Rs${list?.average_cost_for_two}"


                specific_number.text = list?.phone_numbers.toString()


                //restaurantRecylcerView.adapter = myAdapter(list)

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_restaurant_details)
        val x = supportActionBar
        x?.title = intent.getStringExtra("name")
        x?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getStringExtra("id")
        getRestaurantDetails(id)


        seeReviewButton.setOnClickListener {
            val intent = Intent(this,review_activity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
    }
}




class specificMyAdapter(val list : details?, val context: Context) : RecyclerView.Adapter<viewHolder>(){


    var clickInterface : onClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = li.inflate(R.layout.item_view_specific,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount() = list?.photos!!.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Picasso.get().load(list?.photos!![position].photo.url).into(holder.itemView.specific_imageView);

    }

}
