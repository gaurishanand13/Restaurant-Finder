package com.example.intern_project.dataclasses.last_restaurant

data class UserRating(
    val aggregate_rating: String,
    val rating_color: String,
    val rating_obj: RatingObj,
    val rating_text: String,
    val votes: String
)