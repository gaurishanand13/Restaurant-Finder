package com.example.intern_project

import com.example.intern_project.dataclasses.categoriesDataclasses.Category
import com.example.intern_project.dataclasses.categoriesDataclasses.categories
import com.example.intern_project.dataclasses.citiesDataClasses.cities_data_classes
import com.example.intern_project.dataclasses.last_restaurant.last_restaurant_data_class
import com.example.intern_project.dataclasses.nearbyRestaurants
import com.example.intern_project.dataclasses.restaurantDetailsById.details
import com.example.intern_project.dataclasses.review_data_classes.review_data_class
import com.example.intern_project.dataclasses.search_data_class.searchDataClass
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object RetrofitClient{

    val api_key = "062c757f237b1749603efe06d8a064fe"

    //val gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://developers.zomato.com/api/v2.1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService = retrofit.create(myInterface::class.java)

}


public interface myInterface{
    @Headers("user-key: 062c757f237b1749603efe06d8a064fe")
    @GET("geocode")
    fun requestRestaurants(@Query("lat") lat: Double, @Query("lon") lon:  Double) : Call<nearbyRestaurants>

    @Headers("user-key: 062c757f237b1749603efe06d8a064fe")
    @GET("categories")
    fun getCategories() : Call<categories>


    @Headers("user-key: 062c757f237b1749603efe06d8a064fe")
    @GET("collections")
    fun getRestaurantsInCities(@Query("lat") lat: Double, @Query("lon") lon:  Double) : Call<cities_data_classes>



    @Headers("user-key: 062c757f237b1749603efe06d8a064fe")
    @GET("search")
    fun getRestaurants(@Query("lat") lat: Double, @Query("lon") lon:  Double) : Call<last_restaurant_data_class>


    @Headers("user-key: 062c757f237b1749603efe06d8a064fe")
    @GET("restaurant")
    fun restaurantDetailsById(@Query("res_id") res_id :String) : Call<details>




    @Headers("user-key: 062c757f237b1749603efe06d8a064fe")
    @GET("reviews")
    fun getReviews(@Query("res_id") res_id :String) : Call<review_data_class>


    @Headers("user-key: 062c757f237b1749603efe06d8a064fe")
    @GET("search")
    fun search_in_materialSearch(@Query("q") q:String) : Call<searchDataClass>

}