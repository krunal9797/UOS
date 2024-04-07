package com.example.uos.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mycartooncharacter.utils.HelperResizer
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.adapter.GalleryAdapter
import com.example.uos.databinding.ActivityGalleryBinding
import com.example.uos.databinding.ShowImageBinding
import com.example.uos.interfaces.OnClickImage
import com.example.uos.model.Gallery
import com.example.uos.model.User
import com.example.uos.utils.Config
import com.example.uos.utils.CustomProgressDialog
import com.example.uos.utils.SharedPreference

class GalleryActivity : BaseActivity(), OnClickImage {
    lateinit var binding: ActivityGalleryBinding
    lateinit var vm: ViewModel
    private var user: User? = null
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var customProgressDialog: CustomProgressDialog

    companion object{
        private var arrGallery: ArrayList<Gallery> = ArrayList()
    }
    private var a1: AlertDialog? = null
    private var b1: AlertDialog.Builder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[ViewModel::class.java]
        user = SharedPreference.getUser(this@GalleryActivity)
        customProgressDialog = CustomProgressDialog(this)

        customProgressDialog.show()
        galleryAdapter = GalleryAdapter(applicationContext, arrGallery, 1,this)
        binding.rvGallery.adapter = galleryAdapter

        if (arrGallery.isEmpty())
        {
            getGallery()
        }
        else{
            customProgressDialog.dismiss()
            galleryAdapter.notifyDataSetChanged()
        }

        binding.ivBack.setOnClickListener {
            backEvent()
        }


    }

    private fun backEvent() {
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getGallery() {
        vm.getGallery().observe(this@GalleryActivity){result ->
            Log.e("TAG123456789", "onCreate: " + result.message)
            if (result.error == "200")
            {
                arrGallery.clear()
                arrGallery.addAll(result.gallery.reversed())
                galleryAdapter.notifyDataSetChanged()
                customProgressDialog.dismiss()
            }
            else
            {
                customProgressDialog.dismiss()
            }
        }
    }

    override fun onBackPressed() {
        backEvent()
    }

    override fun clickOnImage(image: String, date: String) {
        val bindingImage = ShowImageBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(this, R.style.TransparentDialog)
        b1!!.setView(bindingImage.root)

        HelperResizer.getHeightAndWidth(applicationContext)
        HelperResizer.setSize(bindingImage.rlbg,872,680,true)
        HelperResizer.setSize(bindingImage.ivImage,872,680,true)
        bindingImage.ivClose.setOnClickListener { a1?.dismiss() }

        Glide.with(applicationContext).load(Config.BASE_URL+"gallery/"+image)
            .apply(RequestOptions().override(720, 720))
            .placeholder(R.drawable.uos)
            .into(bindingImage.ivImage)

        bindingImage.tvTitle.text = ""+date

        a1 = b1!!.create()
        a1!!.show()
    }
}