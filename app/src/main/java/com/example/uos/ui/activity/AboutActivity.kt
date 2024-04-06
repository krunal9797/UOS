package com.example.uos.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uos.R
import com.example.uos.databinding.ActivityAboutBinding
import com.example.uos.ui.fragment.SettingActivity

class AboutActivity : BaseActivity() {

    lateinit var binding:ActivityAboutBinding
    private var activity:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = intent.getStringExtra("activity").toString()

        binding.ivBack.setOnClickListener {
            backEvent()
        }

    }

    private fun backEvent() {
        if (activity!= null && activity.equals("setting"))
        {
            startActivity(Intent(applicationContext,SettingActivity::class.java))
        }
        else
        {
            startActivity(Intent(applicationContext,MainActivity::class.java))

        }
        finish()

    }

    override fun onBackPressed() {
        backEvent()
    }
}