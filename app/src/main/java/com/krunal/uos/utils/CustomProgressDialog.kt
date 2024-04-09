package com.krunal.uos.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.krunal.uos.R

class CustomProgressDialog(context: Context) : Dialog(context,R.style.TransparentDialog) {

    private lateinit var imageView: ImageView

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_progress_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        // Initialize the ImageView
        imageView = findViewById(R.id.progressBar)

        // Start rotating animation
        startRotateAnimation()
    }

    private fun startRotateAnimation() {
        val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 1000 // Rotate for 1 second
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.repeatCount = Animation.INFINITE // Repeat indefinitely
        imageView.startAnimation(rotateAnimation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

