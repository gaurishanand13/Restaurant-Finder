package com.example.intern_project.dataclasses.search_data_class

data class searchDataClass(
    val restaurants: List<Restaurant>,
    val results_found: Int,
    val results_shown: Int,
    val results_start: Int
)