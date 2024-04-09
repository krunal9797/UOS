package com.krunal.uos.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.krunal.uos.R
import com.krunal.uos.Vm.ViewModel
import com.krunal.uos.databinding.ActivityDonationBinding
import com.krunal.uos.model.User
import com.krunal.uos.utils.CustomProgressDialog
import com.krunal.uos.utils.SharedPreference
import com.krunal.uos.utils.Utils

class DonationActivity : BaseActivity() {

    private lateinit var binding:ActivityDonationBinding
    private var user: User? = null
    private lateinit var vm: com.krunal.uos.Vm.ViewModel
    private var callMap: HashMap<String, String> = HashMap()
    private lateinit var customProgressDialog: CustomProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[com.krunal.uos.Vm.ViewModel::class.java]
        user = SharedPreference.getUser(this@DonationActivity)
        customProgressDialog = CustomProgressDialog(this)


        binding.ivBack.setOnClickListener {
            backEvent()
        }

        binding.btnCall.setOnClickListener {
            if (user!=null)
            {
                insertData(this.user!!.id,"call")
            } else {
                // Handle case where mobile number is not available
                Utils.showToast(this, getString(R.string.mobile_number_not_available))
            }
        }

        binding.btnWhatsapp.setOnClickListener {
            if (user!=null)
            {
                insertData(user!!.id,"whatsapp")
            } else {
                // Handle case where mobile number is not available
                Utils.showToast(this, getString(R.string.mobile_number_not_available))
            }
        }

    }

    private fun insertData(id: String, choose: String) {
        customProgressDialog.show()
        callMap.clear()
        callMap["uid"] = user?.id.toString()
        callMap["did"] = id

        vm.callInsert(callMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    customProgressDialog.dismiss()

                     if (choose=="call")
                    {
                        val mobile = "+919574115670"
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${mobile}")
                        }
                        startActivity(intent)
                    }
                    else
                    {
//                        val url = "https://wa.me/$mobile" // WhatsApp URL with the phone number
                        val mobile = "+917046114313"
                        val uri = Uri.parse("smsto:$mobile")
                        val intent = Intent(Intent.ACTION_SENDTO, uri)
                        intent.`package` = "com.whatsapp"
                        try {
                            startActivity(Intent.createChooser(intent, ""))
                        } catch (e: Exception) {
                            // Handle exception here, such as displaying an error message
                            Utils.showToast(this, "Error: ${e.message}")
                        }


                    }

                } else {
                    customProgressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                }
            } else {
                customProgressDialog.dismiss()
                Utils.showToast(this, getString(R.string.an_error_occurred))
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
}