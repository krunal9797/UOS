package com.krunal.uos.model

data class UserResponse(
    val error: String,
    val message: String,
    val user: User,
    val email_notification:Boolean? = null
)