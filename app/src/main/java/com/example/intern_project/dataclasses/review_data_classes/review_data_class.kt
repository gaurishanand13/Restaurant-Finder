package com.example.intern_project.dataclasses.review_data_classes

data class review_data_class(
    val reviews_count: Int,
    val reviews_shown: Int,
    val reviews_start: Int,
    val user_reviews: ArrayList<UserReview>
)