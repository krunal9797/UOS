package com.example.uos.interfaces

import com.example.uos.model.Request
import com.example.uos.model.User

interface ClickToRequest {
    fun onClickCall(user: Request)
}