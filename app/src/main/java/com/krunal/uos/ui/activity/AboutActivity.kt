package com.krunal.uos.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.krunal.uos.databinding.ActivityAboutBinding
import com.krunal.uos.utils.Config
import com.krunal.uos.utils.CustomProgressDialog
import com.uos.bloodbank.utils.Constant


class AboutActivity : BaseActivity() {

    lateinit var binding:ActivityAboutBinding
    private var activity:String? = null

    private lateinit var customProgressDialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = intent.getStringExtra("activity").toString()

        binding.ivBack.setOnClickListener {
            backEvent()
        }

        customProgressDialog = CustomProgressDialog(this)

        // Show the custom progress dialog

        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.webChromeClient = MyWebChromeClient()
        binding.webView.loadUrl(Config.BASE_URL+ Constant.WEB_SERVICES.WS_ABOUT)
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            customProgressDialog.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            customProgressDialog.dismiss()
        }
    }

    private inner class MyWebChromeClient : WebChromeClient() {
        // You can override other methods of WebChromeClient if needed
    }


    private fun backEvent() {
        if (activity!= null && activity.equals("setting"))
        {
            startActivity(Intent(applicationContext, SettingActivity::class.java))
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