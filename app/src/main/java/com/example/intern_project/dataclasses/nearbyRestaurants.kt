package com.example.intern_project.dataclasses

data class nearbyRestaurants(
    val link: String,
    val locality: Locality,
    val nearby_restaurants: List<NearbyRestaurant>,
    val popularity: Popularity
)