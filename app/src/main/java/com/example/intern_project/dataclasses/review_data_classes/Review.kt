package com.example.intern_project.dataclasses.review_data_classes

data class Review(
    val comments_count: Int,
    val id: Int,
    val likes: Int,
    val rating: Double,
    val rating_color: String,
    val rating_text: String,
    val review_text: String,
    val review_time_friendly: String,
    val timestamp: Int,
    val user: User
)