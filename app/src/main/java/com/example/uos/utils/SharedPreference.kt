package com.example.uos.utils

import android.content.Context
import com.example.uos.model.User
import com.google.gson.Gson

object SharedPreference {
    private const val PREF_FILE_NAME = "user_prefs"
    private const val KEY_USER_DATA = "user_data"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_IS_TURN_ON = "is_on_notification"

    // Method to save user data
    fun saveUser(context: Context, user: User) {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val userJson = gson.toJson(user)
        editor.putString(KEY_USER_DATA, userJson)
        editor.apply()
        setLoggedIn(context, true)
    }

    // Method to retrieve user data
    fun getUser(context: Context): User? {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val userJson = sharedPreferences.getString(KEY_USER_DATA, null)
        val gson = Gson()
        return gson.fromJson(userJson, User::class.java)
    }

    // Method to clear user data and logout
    fun clearUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_USER_DATA)
        editor.remove(KEY_IS_LOGGED_IN)
        editor.apply()
    }

    // Method to set the login state
    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    // Method to check if the user is logged in
    fun isLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setNotification(context: Context,isCheck:Boolean)
    {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_TURN_ON, isCheck)
        editor.apply()
    }

    fun isCheckNotification(context: Context):Boolean{
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_IS_TURN_ON, true)
    }
}
