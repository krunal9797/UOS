package com.example.uos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.uos.utils.SP_Manager
import com.example.uos.databinding.ActivitySplashBinding
import com.example.uos.utils.SharedPreference


class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({

            if (SP_Manager.getGuideCompleted()) {
                val isLoggedIn = SharedPreference.isLoggedIn(applicationContext)

                if (isLoggedIn) {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    startActivity(Intent(applicationContext, IntroScreenActivity::class.java))
                }

            } else {
                val intent = Intent(applicationContext, LanguageActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()

            }

        }, 3000)

    }


}