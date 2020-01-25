package com.example.intern_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intern_project.dataclasses.last_restaurant.last_restaurant_data_class
import com.example.intern_project.dataclasses.review_data_classes.review_data_class
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_review_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class review_activity : AppCompatActivity() {


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    fun getReviews(id:String){


        val call = RetrofitClient.retrofitService?.getReviews(id)
        call?.enqueue(object : Callback<review_data_class> {
            override fun onFailure(call: Call<review_data_class>, t: Throwable) {
                Log.i("tag",t.message)
                Toast.makeText(this@review_activity,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<review_data_class>, response: Response<review_data_class>) {
                val list = response.body()
                reviewTextView.layoutManager = LinearLayoutManager(this@review_activity)
                reviewTextView.adapter = reviewAdapter(list,this@review_activity)

                review_progressBar.visibility = View.GONE

                if(list!!.user_reviews.size==0){
                    noReviewsTextView.visibility = View.VISIBLE
                    reviewTextView.visibility = View.GONE
                }else{
                    noReviewsTextView.visibility = View.GONE
                    reviewTextView.visibility = View.VISIBLE
                }
            }
        })

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_activity)
        val x = supportActionBar
        x?.title = "Reviews"
        x?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra("id")
        getReviews(id)
    }
}
