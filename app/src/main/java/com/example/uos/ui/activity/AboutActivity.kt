package com.example.uos.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.uos.R
import com.example.uos.databinding.ActivityAboutBinding
import com.example.uos.ui.fragment.SettingActivity
import com.example.uos.utils.Config
import com.uos.bloodbank.utils.Constant


class AboutActivity : BaseActivity() {

    lateinit var binding:ActivityAboutBinding
    private var activity:String? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = intent.getStringExtra("activity").toString()

        binding.ivBack.setOnClickListener {
            backEvent()
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.webChromeClient = MyWebChromeClient()
        binding.webView.loadUrl(Config.BASE_URL+ Constant.WEB_SERVICES.WS_ABOUT)
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressDialog.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressDialog.dismiss()
        }
    }

    private inner class MyWebChromeClient : WebChromeClient() {
        // You can override other methods of WebChromeClient if needed
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