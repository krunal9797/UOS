package com.krunal.uos.model

data class UosDataModel(
    val `data`: List<Data>,
    val error: String,
    val message: String
)