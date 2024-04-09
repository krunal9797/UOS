package com.krunal.uos.model

data class EventModelResponse(
    val error: String,
    val event: List<Event>,
    val message: String
)