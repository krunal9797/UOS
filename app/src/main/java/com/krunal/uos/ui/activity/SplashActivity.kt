package com.krunal.uos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.krunal.uos.utils.SP_Manager
import com.krunal.uos.databinding.ActivitySplashBinding
import com.krunal.uos.utils.SharedPreference


class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Use a Handler to delay the transition from the splash screen to your app's content
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, 3500) // Adjust this delay as needed



    }

    private fun navigateToNextScreen() {
        if (SP_Manager.getGuideCompleted()) {
            val isLoggedIn = SharedPreference.isLoggedIn(applicationContext)
            val nextActivity = if (isLoggedIn) MainActivity::class.java else IntroScreenActivity::class.java
            startActivity(Intent(applicationContext, nextActivity))
        } else {
            val intent = Intent(applicationContext, LanguageActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }
        finish()
    }


}