package com.example.intern_project
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intern_project.dataclasses.categoriesDataclasses.Category
import com.example.intern_project.dataclasses.categoriesDataclasses.categories
import com.example.intern_project.dataclasses.citiesDataClasses.cities_data_classes
import com.example.intern_project.dataclasses.last_restaurant.last_restaurant_data_class
import com.example.intern_project.dataclasses.nearbyRestaurants
import com.example.intern_project.dataclasses.search_data_class.searchDataClass
import com.google.android.gms.location.LocationServices
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            123 -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //Now we can grant show the restaurants, depending on the screen
                    reloadFrameLayout.visibility = View.GONE
                    finalLinearLayout.visibility = View.VISIBLE
                    getLocationSmartly()
                } else {
                    reloadFrameLayout.visibility = View.VISIBLE
                    finalLinearLayout.visibility = View.GONE
                }
                return
            }
        }
    }
    fun grantPermission(){
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),
                123)
        }else{
            reloadFrameLayout.visibility = View.GONE
            finalLinearLayout.visibility = View.VISIBLE
            getLocationSmartly()
        }
    }

    fun getCategories(){

        val call = RetrofitClient.retrofitService?.getCategories()
        call?.enqueue(object : Callback<categories>{
            override fun onFailure(call: Call<categories>, t: Throwable) {
                Log.i("tag",t.message)
                Log.i("tag","hello mr")
                Toast.makeText(this@MainActivity,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<categories>, response: Response<categories>) {
                val list = response.body()
                Log.i("categories",list.toString())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu?.findItem(R.id.search)
        logged_in_searchView.setMenuItem(searchItem)
        return true
    }

    fun setUpOnQueryListenerOn_Material_SearchView(){
        logged_in_searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    Log.i("my_ error",newText)
                    search_query_update(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!=null){
                    Log.i("my_ error",query)
                    Log.i("tag","hello 1")
                    perform_final_search_for_final_query(query)
                }
                return true
            }
        })
    }

    private fun search_query_update(query: String) {
        var string = ""
        for(x in query){
            if(x==' '){
                string = string + "%20"
            }else if(x=='!'){
                string = string + "%21"
            }else if (x=='&'){
                string = string + "%26"
            }
            else if (x=='$'){
                string = string + "%24"
            }else if (x==':'){
                string = string + "%3A"
            }else{
                string = string + x
            }
        }
        val call = RetrofitClient.retrofitService?.search_in_materialSearch(string)
        call?.enqueue(object : Callback<searchDataClass>{
            override fun onFailure(call: Call<searchDataClass>, t: Throwable) {
                Log.i("tag","hello 3")
                Toast.makeText(this@MainActivity,"Not able to fetch data, Try Again!",Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<searchDataClass>, response: Response<searchDataClass>) {
                val list = response.body()
                if(list!!.restaurants.size==0){
                    var smallList = Array<String>(1){
                        "No restaurant found by this name"
                    }
                    Log.i("tag","hello 2")
                    logged_in_searchView.setSuggestions(smallList)
                }else{
                    var smallList = Array(list!!.restaurants.size){
                        list!!.restaurants[it].restaurant.name
                    }
                    logged_in_searchView.setSuggestions(smallList)
                }

            }

        })
    }

    fun perform_final_search_for_final_query(query : String)
    {
        var string = ""
        for(x in query){
            if(x==' '){
                string = string + "%20"
            }else if(x=='!'){
                string = string + "%21"
            }else if (x=='&'){
                string = string + "%26"
            }
            else if (x=='$'){
                string = string + "%24"
            }else if (x==':'){
                string = string + "%3A"
            }else{
                string = string + x
            }
        }
        Log.i("my_ error","hello")
        val call = RetrofitClient.retrofitService?.search_in_materialSearch(string)
        call?.enqueue(object : Callback<searchDataClass>{
            override fun onFailure(call: Call<searchDataClass>, t: Throwable) {
                Toast.makeText(this@MainActivity,"Not able to fetch data, Try Again!",Toast.LENGTH_LONG).show()
                Log.i("my_ error",t.message)
            }
            override fun onResponse(call: Call<searchDataClass>, response: Response<searchDataClass>) {
                val list = response.body()
                if(list!!.restaurants.size==0){
                    Toast.makeText(this@MainActivity,"Not able to fetch data, Try Again!",Toast.LENGTH_LONG).show()
                    Log.i("my_ error","hello")
                }
                else{
                    val intent = Intent(this@MainActivity,specific_restaurant_details::class.java)
                    intent.putExtra("id",list!!.restaurants[0].restaurant.id)
                    intent.putExtra("name",list!!.restaurants[0].restaurant.name)
                    startActivity(intent)
                    Log.i("my_ error","hello 100")
                }
            }

        })
    }



    fun getRestaurantDetails(lat: Double,longitude : Double){

        val call = RetrofitClient.retrofitService?.requestRestaurants(lat, longitude)
        call?.enqueue(object : Callback<nearbyRestaurants>{
            override fun onFailure(call: Call<nearbyRestaurants>, t: Throwable) {
                Log.i("tag",t.message)
                Toast.makeText(this@MainActivity,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<nearbyRestaurants>, response: Response<nearbyRestaurants>) {
                val list = response.body()
                Log.i("print",list.toString())
                text.text = list.toString()
                restaurantRecylcerView.layoutManager = LinearLayoutManager(this@MainActivity)
                //restaurantRecylcerView.adapter = myAdapter(list)

            }
        })

    }

    fun getRestaurantDetailsInCity(lat: Double,longitude : Double){

        val call = RetrofitClient.retrofitService?.getRestaurantsInCities(lat, longitude)
        call?.enqueue(object : Callback<cities_data_classes>{
            override fun onFailure(call: Call<cities_data_classes>, t: Throwable) {
                Log.i("tag",t.message)
                Toast.makeText(this@MainActivity,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<cities_data_classes>, response: Response<cities_data_classes>) {
                val list = response.body()
                Log.i("print",list.toString())
                text.text = list.toString()
                restaurantRecylcerView.layoutManager = LinearLayoutManager(this@MainActivity)
                //restaurantRecylcerView.adapter = myAdapter(list)

            }
        })

    }

    fun getRestaurants_last(lat: Double,longitude : Double){

        val call = RetrofitClient.retrofitService?.getRestaurants(lat, longitude)
        call?.enqueue(object : Callback<last_restaurant_data_class>{
            override fun onFailure(call: Call<last_restaurant_data_class>, t: Throwable) {
                Log.i("tag",t.message)
                Toast.makeText(this@MainActivity,t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<last_restaurant_data_class>, response: Response<last_restaurant_data_class>) {
                val list = response.body()
                Log.i("print",list.toString())
                text.text = list.toString()
                restaurantRecylcerView.layoutManager = LinearLayoutManager(this@MainActivity)
                val adapter = myAdapter(list,this@MainActivity)
                restaurantRecylcerView.adapter = adapter
                adapter?.clickInterface = object : onClick{
                    override fun onClick(id: String,name:String) {
                        val intent = Intent(this@MainActivity,specific_restaurant_details::class.java)
                        intent.putExtra("id",id)
                        intent.putExtra("name",name)
                        startActivity(intent)
                    }
                }
            }
        })

    }

    private fun getLocationSmartly() {

        val client = LocationServices.getFusedLocationProviderClient(this)
        client.lastLocation.apply {
            addOnFailureListener{
                //the code written here will be executed if the it fails to fetch the location of the user.
                Toast.makeText(this@MainActivity,"Not Able To Fetch Location, Please Check your connections or try again",Toast.LENGTH_LONG).show()
            }
            addOnSuccessListener {
                val geoCoder = Geocoder(this@MainActivity, Locale.getDefault())
                try {
                    val addresses = geoCoder.getFromLocation(it.latitude, it.longitude,1)
                    val addressObject = addresses[0]
                    var addressOutput = StringBuilder()
                    if(addressObject.subThoroughfare!=null){
                        addressOutput.append(addressObject.subThoroughfare + ", ")
                    }
                    if(addressObject.subAdminArea!=null){
                        addressOutput.append(addressObject.subAdminArea + ", ")
                    }
                    if(addressObject.adminArea!=null){
                        addressOutput.append(addressObject.adminArea + ", ")
                    }
                    if(addressObject.countryName!=null){
                        addressOutput.append(addressObject.countryName + ", ")
                    }
                    addressOutput.delete(addressOutput.length-2,addressOutput.length-1)
                    locationTextView.text = addressOutput.dropLast(1)
                    getRestaurants_last(it.latitude,it.longitude)

                }catch (e : Exception){
                    Toast.makeText(this@MainActivity,"Not Able To Fetch Location, Please Check your connections or try again",Toast.LENGTH_LONG).show()
                }
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val x = supportActionBar
        x?.title = "Find nearby restaurants"
        reloadButton.setOnClickListener {
            grantPermission()
        }
        logged_in_searchView.clearFocus()
        setUpOnQueryListenerOn_Material_SearchView()
        grantPermission()
    }
}
