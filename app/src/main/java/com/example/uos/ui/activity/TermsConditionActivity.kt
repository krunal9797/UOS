package com.example.uos.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uos.R
import com.example.uos.databinding.ActivityTermsConditionBinding
import com.example.uos.ui.fragment.SettingActivity

class TermsConditionActivity : BaseActivity() {
    lateinit var binding: ActivityTermsConditionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { backEvent() }

    }

    private fun backEvent() {
        val intent = Intent(applicationContext,SettingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        backEvent()
    }
}