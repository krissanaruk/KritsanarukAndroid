package com.example.kritsanaruksssss

data class Car(
    val id: Int,
    val brand: String,
    val model: String,
    val year: String,
    val color: String,
    val price: Double, // Assuming price should be a numeric type
    val transmissionType: String,
    val fuel_type: String,
    val doors: Int,
    val seats: Int,
    val image_url: String? // Assuming carImage can be null
)
