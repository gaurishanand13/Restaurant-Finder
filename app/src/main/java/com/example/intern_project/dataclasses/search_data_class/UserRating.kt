package com.example.intern_project.dataclasses.search_data_class

data class UserRating(
    val aggregate_rating: Double,
    val rating_color: String,
    val rating_obj: RatingObj,
    val rating_text: String,
    val votes: Int
)