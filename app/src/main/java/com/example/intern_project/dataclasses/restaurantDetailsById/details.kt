package com.example.intern_project.dataclasses.restaurantDetailsById

data class details(

    val all_reviews_count: String,
    val average_cost_for_two: String,
    val cuisines: String,
    val currency: String,
    val deeplink: String,
    val events_url: String,
    val featured_image: String,
    val has_online_delivery: String,
    val has_table_booking: String,
    val id: String,
    val is_delivering_now: String,
    val location: Location,
    val menu_url: String,
    val name: String,
    val phone_numbers: String,
    val photo_count: String,
    val photos: List<x>,
    val photos_url: String,
    val price_range: String,
    val thumb: String,
    val url: String,
    val user_rating: UserRating
)

data class x(
    val photo : Photo
)

//val all_reviews: List<AllReview>,