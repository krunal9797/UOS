package com.example.uos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.mycartooncharacter.model.IntroModel
import com.example.uos.utils.SP_Manager
import com.example.uos.R
import com.example.uos.adapter.IntroPagerAdapter
import com.example.uos.databinding.ActivityIntroScreenBinding

class IntroScreenActivity : BaseActivity() {
    lateinit var binding: ActivityIntroScreenBinding
    private lateinit var introScreenAdapter: IntroPagerAdapter
    private var arrIntroScreen = ArrayList<IntroModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setEvent()

    }

    private fun setEvent() {

        arrIntroScreen.add(
            IntroModel(
                getString(R.string.g_one),
                getString(R.string.g_one_description),
                R.drawable.introscreen_1
            )
        )

        arrIntroScreen.add(
            IntroModel(
                getString(R.string.g_two),
                getString(R.string.g_two_description),
                R.drawable.introscreen_1
            )
        )

        arrIntroScreen.add(
            IntroModel(
                getString(R.string.g_three),
                getString(R.string.g_three_description),
                R.drawable.introscreen_1
            )
        )

        introScreenAdapter = IntroPagerAdapter(arrIntroScreen, applicationContext)
        binding.viewpager.adapter = introScreenAdapter
        introScreenAdapter.notifyDataSetChanged()



        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            private val progressImageViews = listOf(
                binding.ivProgressImage,
                binding.ivProgressImage2,
                binding.ivProgressImage3
            )

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val totalItemCount = binding.viewpager.adapter?.itemCount ?: 0

                when (position) {
                    0 -> {
                        binding.viewpager.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.intro1))
                    }
                    1 -> {
                        binding.viewpager.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.intro2))
                    }
                    2 -> {
                        binding.viewpager.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.intro3))
                    }
                    else -> {
                        binding.viewpager.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.white))
                    }
                }

                // Show or hide buttons based on position
                binding.btnNext.visibility =
                    if (position == totalItemCount - 1) View.GONE else View.VISIBLE
                binding.btnDone.visibility =
                    if (position == totalItemCount - 1) View.VISIBLE else View.GONE

                // Show the appropriate progress image and hide others
                progressImageViews.forEachIndexed { index, imageView ->
                    imageView.visibility = if (index == position) View.VISIBLE else View.GONE
                }
            }
        })

        binding.btnNext.setOnClickListener {
            // Assuming you have a ViewPager reference named 'viewPager' and a Context reference named 'context'
            val currentPage = binding.viewpager.currentItem
            val nextPage = currentPage + 1

            if (nextPage < binding.viewpager.adapter?.itemCount ?: 0) {
                // Move to the next page
                binding.viewpager.currentItem = nextPage
            }


        }

        binding.tvSkip.setOnClickListener {
            SP_Manager.setGuideCompleted(true)
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnDone.setOnClickListener {
            SP_Manager.setGuideCompleted(true)
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }


}