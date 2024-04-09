package com.krunal.uos.database

import android.content.Context
import android.content.SharedPreferences
import com.krunal.uos.model.User

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        with(sharedPreferences.edit()) {
            putString("address", user.address)
            putString("area", user.area)
            putString("bloodgroup", user.bloodgroup)
            putString("city", user.city)
            putString("dob", user.dob)
            putString("email", user.email)
            putString("id", user.id)
            putString("last_donate", user.last_donate)
            putString("mobile", user.mobile)
            putString("name", user.name)
            putString("password", user.password)
            putString("state", user.state)
            putString("timestamp", user.timestamp)
            putString("user_donor", user.user_donor)
            putBoolean("is_first_time_login", false) // Set to false as the user has logged in
            apply()
        }
    }

    fun getUser(): User {

        val address = sharedPreferences.getString("address","")?:""
        val area = sharedPreferences.getString("area","")?:""
        val bloodgroup = sharedPreferences.getString("bloodgroup","")?:""
        val city = sharedPreferences.getString("city","")?:""
        val dob = sharedPreferences.getString("dob","")?:""
        val email = sharedPreferences.getString("email","")?:""
        val id = sharedPreferences.getString("id","")?:""
        val last_donate = sharedPreferences.getString("last_donate","")?:""
        val mobile = sharedPreferences.getString("mobile","")?:""
        val name = sharedPreferences.getString("name","")?:""
        val password = sharedPreferences.getString("password","")?:""
        val state = sharedPreferences.getString("state","")?:""
        val timestamp = sharedPreferences.getString("timestamp","")?:""
        val user_donor = sharedPreferences.getString("user_donor","")?:""

        return User(address, area, bloodgroup, city, dob, email, id, last_donate, mobile, name, password, state, timestamp, user_donor)

    }

    fun isFirstTimeLogin(): Boolean {
        return sharedPreferences.getBoolean("is_first_time_login", true)
    }

    fun setFirstTimeLogin(isFirstTimeLogin: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean("is_first_time_login", isFirstTimeLogin)
            apply()
        }
    }
}
