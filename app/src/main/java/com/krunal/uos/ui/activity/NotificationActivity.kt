package com.krunal.uos.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krunal.uos.R
import com.krunal.uos.databinding.ActivityMainBinding
import com.krunal.uos.databinding.ActivityNotificationBinding
import com.krunal.uos.ui.activity.BaseActivity


class NotificationActivity : BaseActivity() {

    lateinit var binding:ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}