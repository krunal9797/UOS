package com.krunal.uos.utils

import android.content.Context
import android.content.SharedPreferences

object SP_Manager
{
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var myEdit: SharedPreferences.Editor

    const val LANGUAGE_SELECTED = "language_selected"
    const val LANGUAGE_CODE = "language_code"
    const val GUIDE_COMPLETED = "guide_completed"
    const val LANGUAGE_CODE_SNIP = "language_code_snip"

    enum class POSITION {
        TOP_RIGHT,
        BOTTOM_RIGHT,
        TOP_LEFT,
        BOTTOM_LEFT,
        CENTER,
        RIGHT,
        LEFT
    }

    enum class TIMER {
        TIMER_0,
        TIMER_3,
        TIMER_5,
        TIMER_10
    }

    enum class ASPECT_RATIO {
        AR_3_4,
        AR_1_1,
        AR_9_16
    }

    // Storing data into SharedPreferences
    fun initializingSharedPreference(context: Context) {
        sharedPreferences = context.getSharedPreferences("MySharedPref123", Context.MODE_PRIVATE)
        // Creating an Editor object to edit(write to the file)
        myEdit = sharedPreferences.edit()
    }

    fun getLanguageSelected(): Boolean {
        return sharedPreferences.getBoolean(LANGUAGE_SELECTED, false)
    }

    fun setLanguageSelected(set: Boolean) {
       sharedPreferences.edit().putBoolean(LANGUAGE_SELECTED, set).apply()
    }

    fun getGuideCompleted(): Boolean {
        return sharedPreferences.getBoolean(GUIDE_COMPLETED, false)
    }

    fun setGuideCompleted(set: Boolean) {
        sharedPreferences.edit().putBoolean(GUIDE_COMPLETED, set).apply()
    }

    fun getLanguageCode(): String? {
        return sharedPreferences.getString(
            LANGUAGE_CODE,
            "English"
        )
    }

    fun setLanguageCode(set: String?) {
        sharedPreferences.edit().putString(LANGUAGE_CODE, set).apply()
    }

    fun getLanguageCodeSnip(): String? {
        return sharedPreferences.getString(LANGUAGE_CODE_SNIP, "en")
    }

    fun setLanguageCodeSnip(set: String?) {
        sharedPreferences.edit().putString(LANGUAGE_CODE_SNIP, set).apply()
    }
    
    
}