package com.example.uos.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mycartooncharacter.model.LanguageModel
import com.example.mycartooncharacter.utils.HelperResizer
import com.example.uos.utils.SP_Manager
import com.example.uos.R
import com.example.uos.adapter.LanguageAdapter
import com.example.uos.databinding.ActivityLanguageBinding
import com.example.uos.interfaces.OnCheckLanguage
import com.example.uos.utils.BottomMarginItemDecoration
import kotlin.system.exitProcess

class LanguageActivity : BaseActivity(), OnCheckLanguage {

    lateinit var binding: ActivityLanguageBinding
    var arrLanguage: ArrayList<LanguageModel> = ArrayList<LanguageModel>()
    var languageAdapter: LanguageAdapter? = null
    var context: Context? = null
    private var globalLanguage: LanguageModel? = null
    var From = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDesign()

        From = intent.getIntExtra("from", -1)

        if (From == 1) { binding.ivBack.visibility = View.VISIBLE } else { binding.ivBack.visibility = View.GONE }

        arrLanguage.add(
            LanguageModel(
                R.drawable.ic_launcher_background,
                "English (English)",
                "en",
                false
            )
        )
        arrLanguage.add(
            LanguageModel(
                R.drawable.ic_launcher_background,
                "Hindi (हिंदी)",
                "hi",
                false
            )
        )
        arrLanguage.add(
            LanguageModel(
                R.drawable.ic_launcher_background,
                "Marathi (हिंदी)",
                "mr",
                false
            )
        )
        arrLanguage.add(
            LanguageModel(
                R.drawable.ic_launcher_background,
                "Punjabi (ਪੰਜਾਬੀ)",
                "pa",
                false
            )
        )


        languageAdapter = LanguageAdapter(arrLanguage, applicationContext, this@LanguageActivity)
        binding.rvLanguage.adapter = languageAdapter
        val bottomMarginDecoration = BottomMarginItemDecoration(10, applicationContext)
        binding.rvLanguage.addItemDecoration(bottomMarginDecoration)
        languageAdapter?.notifyDataSetChanged()

        arrLanguage.forEach {
            if (it.LanguageCode == getSelectedLanguage(applicationContext).toString()) {
                it.isChecked = true
                globalLanguage = it
            }
        }
        binding.ivDone.setOnClickListener {
            if (globalLanguage != null) {
                SP_Manager.initializingSharedPreference(applicationContext)
                setLocale(applicationContext, globalLanguage?.LanguageCode)
                SP_Manager.setLanguageCode(globalLanguage?.LanguageName)
                SP_Manager.setLanguageCodeSnip(globalLanguage?.LanguageCode)
                SP_Manager.setLanguageSelected(true)
                if (From == 1) {
                    val i = Intent(this@LanguageActivity, MainActivity::class.java)
                    startActivity(i)
                } else {
                    startActivity(
                        Intent(
                            this@LanguageActivity,
                            IntroScreenActivity::class.java
                        )
                    )
                    finish()
                }
            }

        }

        binding.ivBack.setOnClickListener {
            backEvent()
        }

    }

    private fun setDesign() {
        HelperResizer.getHeightAndWidth(applicationContext)
        HelperResizer.setSize(binding.ivDone, 75, 75, true)
        HelperResizer.setSize(binding.ivBack, 69, 69, true)
        HelperResizer.setSize(binding.rlAppBar, 1080, 156, true)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        backEvent()
    }

    private fun backEvent() {
        if (From == 1) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            finish()
            finishAffinity()
            exitProcess(0)
        }
    }

    override fun onClickItem(languageModel: LanguageModel) {
        globalLanguage = languageModel
        languageAdapter?.notifyDataSetChanged()
    }

}