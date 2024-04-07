package com.example.uos.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.uos.R
import com.example.uos.databinding.ActivityMainBinding
import com.example.uos.databinding.ExitDialogueBinding
import com.example.uos.model.User
import com.example.uos.utils.SharedPreference
import java.util.Calendar

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    private var user: User? = null
    private var a1: AlertDialog? = null
    private var b1: AlertDialog.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        user = SharedPreference.getUser(applicationContext)

        val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greetingMessage = getGreetingMessage(currentTime)
        binding.tvWelcome.text = ""+greetingMessage+" "+user?.name

        binding.tvEmail.text = ""+user?.email


        // Animating tvWelcome
        binding.tvWelcome.apply {
            alpha = 0f // Initially make it transparent
            scaleX = 0f // Initially scale it down horizontally
            scaleY = 0f // Initially scale it down vertically
            animate()
                .alpha(1f) // Make it fully visible
                .scaleX(1f) // Scale it back to its original size horizontally
                .scaleY(1f) // Scale it back to its original size vertically
                .setDuration(1000) // Set animation duration in milliseconds
                .start() // Start the animation
        }
        binding.tvWelcome.isSelected = true

// Animating tvEmail
        binding.tvEmail.apply {
            alpha = 0f // Initially make it transparent
            translationY = 100f // Initially move it down by 100 pixels
            animate()
                .alpha(1f) // Make it fully visible
                .translationY(0f) // Move it back to its original position
                .setDuration(1000) // Set animation duration in milliseconds
                .setStartDelay(500) // Delay the start of animation by 500 milliseconds
                .start() // Start the animation
        }


        Glide.with(applicationContext).load(R.drawable.ic_setting_).into(binding.ivSetting)
        binding.cvBloodRequest.setOnClickListener {
            val intent = Intent(applicationContext, RequestActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cvSearch.setOnClickListener {
            val intent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cvEvent.setOnClickListener {
            val intent = Intent(applicationContext, EventActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cvGallery.setOnClickListener {
            val intent = Intent(applicationContext, GalleryActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cvDonation.setOnClickListener {
            val intent = Intent(applicationContext, DonationActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cvNearHospital.setOnClickListener {
            val mapIntentUri = Uri.parse("geo:0,0?q=near by hospital")
            val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            if (mapIntent.resolveActivity(applicationContext.packageManager) != null) {
                // Google Maps app is installed, open it
                startActivity(mapIntent)
            } else {
                // Google Maps app is not installed, open a web browser
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=near+by+hospital")
                )
                startActivity(webIntent)
            }
        }

        binding.cvAbout.setOnClickListener {
            val intent = Intent(applicationContext, AboutActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.ivSetting.setOnClickListener {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.ivNotification.visibility =
            if (SharedPreference.isCheckNotification(applicationContext)) View.VISIBLE else View.GONE

        binding.ivNotification.setOnClickListener {
         /*   val intent = Intent(applicationContext, NotificationActivity::class.java)
            startActivity(intent)
            finish()*/
        }
    }

    private fun getGreetingMessage(hourOfDay: Int): String {
        return when (hourOfDay) {
            in 0..4 -> "Good Night"
            in 5..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            in 17..20 -> "Good Evening"
            in 21..23 -> "Good Night"
            else -> "Welcome To UOS"
        }
    }
    override fun onBackPressed() {
        showExitDialogue()
    }

    private fun showExitDialogue() {
        val bindingExit = ExitDialogueBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(this@MainActivity, R.style.TransparentDialog)
        b1!!.setView(bindingExit.root)
        b1?.setCancelable(true)

        bindingExit.tvYes.setOnClickListener {
            finish()
            finishAffinity()
        }
        a1 = b1!!.create()
        a1!!.show()

    }

}