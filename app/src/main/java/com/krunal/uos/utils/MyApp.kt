package com.krunal.uos.utils

import android.app.Application


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}