package com.example.intern_project.dataclasses

data class AllReview(
    val comments_count: String,
    val id: String,
    val likes: String,
    val rating: String,
    val rating_color: String,
    val rating_text: String,
    val review_text: String,
    val review_time_friendly: String,
    val timestamp: String,
    val user: User
)