package com.example.uos.model

data class requestModelResponse(
    val error: String,
    val message: String,
    val requests: List<Request>
)