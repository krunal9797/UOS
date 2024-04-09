package com.krunal.uos.model

data class SearchUserModel(
    val error: String,
    val message: String,
    val users: List<User>
)