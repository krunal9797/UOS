package com.krunal.uos.interfaces

import com.krunal.uos.model.Request
import com.krunal.uos.model.User

interface ClickToRequest {
    fun onClickCall(user: Request)
}