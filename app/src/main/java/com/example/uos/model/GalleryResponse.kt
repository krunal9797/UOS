package com.example.uos.model

data class GalleryResponse(
    val error: String,
    val gallery: List<Gallery>,
    val message: String
)