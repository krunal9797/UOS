package com.example.uos.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mycartooncharacter.utils.HelperResizer
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.adapter.EventAdapter
import com.example.uos.adapter.GalleryAdapter
import com.example.uos.databinding.ActivityEventBinding
import com.example.uos.databinding.ShowImageBinding
import com.example.uos.interfaces.OnClickImage
import com.example.uos.model.Event
import com.example.uos.model.Gallery
import com.example.uos.model.User
import com.example.uos.utils.Config
import com.example.uos.utils.CustomProgressDialog
import com.example.uos.utils.SharedPreference

class EventActivity : BaseActivity(), OnClickImage {
    lateinit var binding:ActivityEventBinding
    lateinit var vm: ViewModel
    private var user: User? = null
    private lateinit var eventAdapter: EventAdapter
    private lateinit var customProgressDialog: CustomProgressDialog

    companion object{
        private var arrEvent: ArrayList<Event> = ArrayList()
    }
    private var a1: AlertDialog? = null
    private var b1: AlertDialog.Builder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            backEvent()
        }

        vm = ViewModelProvider(this)[ViewModel::class.java]
        user = SharedPreference.getUser(this)
        customProgressDialog = CustomProgressDialog(this)


        customProgressDialog.show()

        eventAdapter = EventAdapter(applicationContext, arrEvent, this)
        binding.rvEvent.adapter = eventAdapter

        if (arrEvent.isEmpty())
        {
            getEvent()
        }
        else{
            customProgressDialog.dismiss()
            eventAdapter.notifyDataSetChanged()
        }
    }

    private fun getEvent() {
        vm.getEvent().observe(this){result ->
            Log.e("TAG123456789", "onCreate: " + result.message)
            if (result.error == "200")
            {
                arrEvent.clear()
                arrEvent.addAll(result.event.reversed())
                eventAdapter.notifyDataSetChanged()
                customProgressDialog.dismiss()
            }
            else{
                customProgressDialog.dismiss()
            }
        }
    }

    private fun backEvent() {
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        backEvent()
    }

    override fun clickOnImage(image: String, date: String) {
       /* val bindingImage = ShowImageBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(this, R.style.TransparentDialog)
        b1!!.setView(bindingImage.root)

        HelperResizer.getHeightAndWidth(applicationContext)
        HelperResizer.setSize(bindingImage.cvBg,872,680,true)
        HelperResizer.setSize(bindingImage.rlbg,872,680,true)
        HelperResizer.setSize(bindingImage.ivImage,872,680,true)
        bindingImage.ivClose.setOnClickListener { a1?.dismiss() }

        Glide.with(applicationContext).load(Config.BASE_URL+"event/"+image)
            .apply(RequestOptions().override(720, 720))
            .into(bindingImage.ivImage)

        bindingImage.tvTitle.text = ""+date

        a1 = b1!!.create()
        a1!!.show()*/
    }
}