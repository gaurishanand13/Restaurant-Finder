package com.example.intern_project.dataclasses.citiesDataClasses

data class cities_data_classes(
    val collections: List<Collection>,
    val display_text: String,
    val has_more: Int,
    val has_total: Int,
    val share_url: String,
    val user_has_addresses: Boolean
)