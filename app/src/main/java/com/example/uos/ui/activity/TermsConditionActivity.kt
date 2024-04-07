package com.example.uos.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.uos.databinding.ActivityTermsConditionBinding
import com.example.uos.utils.Config
import com.example.uos.utils.CustomProgressDialog
import com.uos.bloodbank.utils.Constant

class TermsConditionActivity : BaseActivity() {
    lateinit var binding: ActivityTermsConditionBinding
    private lateinit var customProgressDialog: CustomProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsConditionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { backEvent() }

        customProgressDialog = CustomProgressDialog(this)

        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.webChromeClient = MyWebChromeClient()
        binding.webView.loadUrl(Config.BASE_URL+ Constant.WEB_SERVICES.WS_TERMS_CONDITIONS)

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
        val intent = Intent(applicationContext, SettingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        backEvent()
    }
}