package com.example.uos.ui.activity

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.example.uos.utils.SP_Manager.getLanguageCodeSnip
import com.example.uos.utils.SP_Manager.initializingSharedPreference
import java.util.Locale

abstract class BaseActivity() : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {

        // Get the selected language from your preferences or any other source
        val selectedLanguage: String? = getSelectedLanguage(newBase) // Replace with your logic


        // Set the locale for the activity context

        // Set the locale for the activity context
        val newContext: Context = setLocale(newBase, selectedLanguage)

        super.attachBaseContext(newContext)
    }

    fun setLocale(newBase: Context, selectedLanguage: String?): Context {
        val locale = Locale(selectedLanguage)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        return newBase.createConfigurationContext(configuration)
    }

    fun getSelectedLanguage(newBase: Context): String? {
        // Replace this with your logic to retrieve the selected language from preferences or any other source
        // Example: return MyPreferences.getSelectedLanguage();
        initializingSharedPreference(newBase.applicationContext)
        return getLanguageCodeSnip()
    }
}
