package com.example.uos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.databinding.ActivityRequestAddBinding
import com.example.uos.databinding.DialogueBloodGroupBinding
import com.example.uos.databinding.DialogueThanksBinding
import com.example.uos.model.User
import com.example.uos.utils.CustomProgressDialog
import com.example.uos.utils.SharedPreference
import com.example.uos.utils.Utils


class RequestAddActivity : BaseActivity() {

    private lateinit var binding: ActivityRequestAddBinding
    private var user: User? = null
    lateinit var vm: ViewModel
    private lateinit var customProgressDialog: CustomProgressDialog
    private var requestMap: HashMap<String, String> = HashMap()

    private var a1: AlertDialog? = null
    private var b1: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[ViewModel::class.java]
        user = SharedPreference.getUser(applicationContext)
        customProgressDialog = CustomProgressDialog(this)


        binding.edtBloodGroup.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showBloodDialogue()
            }
            true
        }

        binding.btnSend.setOnClickListener {
            val uid = user?.id
            val name = binding.edtName.text.toString()
            val mobile = binding.edtMobile.text.toString()
            val bloodGroup = binding.edtBloodGroup.text.toString()
            val state = binding.edtState.text.toString()
            val city = binding.edtCity.text.toString()
            val area = binding.edtArea.text.toString()
            val address = binding.edtAddress.text.toString()
            val hospital = binding.edtHospitalName.text.toString()


            if (name.isEmpty()) {
                binding.edtName.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Name")
            } else if (mobile.isEmpty()) {
                binding.edtMobile.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Mobile")

            } else if (bloodGroup.isEmpty() || bloodGroup == "Select Blood Group") {
                binding.edtBloodGroup.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Choose Blood Group")

            } else if (state.isEmpty()) {
                binding.edtState.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Choose State")
            } else if (city.isEmpty()) {
                binding.edtCity.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Choose City")

            } else if (area.isEmpty()) {
                binding.edtArea.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Choose Area")

            } else if (address.isEmpty()) {
                binding.edtAddress.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Choose Address")

            } else if (hospital.isEmpty()) {
                binding.edtHospitalName.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Choose Hospital")

            } else {
                requestMap["uid"] = uid.toString()
                requestMap["name"] = name
                requestMap["mobile"] = mobile
                requestMap["bloodgroup"] = bloodGroup
                requestMap["state"] = state
                requestMap["city"] = city
                requestMap["area"] = area
                requestMap["address"] = address
                requestMap["hospital_name"] = hospital

                customProgressDialog.show()
                vm.sendRequestBlood(requestMap).observe(this) { result ->
                    Log.e("TAG123456789", "onCreate: " + result.message)
                    Log.e("TAG123456789", "onCreate: " + result.error)
                    if (result != null) {
                        if (result.error == "200") {
                            customProgressDialog.dismiss()
                            //                        Toast.makeText(applicationContext, "" + result.message, Toast.LENGTH_SHORT).show()
                            showDialogue()
                        } else {

                            customProgressDialog.dismiss()
                            Toast.makeText(
                                applicationContext, "" + result.message, Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        customProgressDialog.dismiss()
                        Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }
    }


    private fun showBloodDialogue() {
        val bindingBlood = DialogueBloodGroupBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(this, R.style.TransparentDialog)
        b1!!.setView(bindingBlood.root)

        bindingBlood.tvAPositive.setOnClickListener {
            binding.edtBloodGroup.setText("A+")
            a1?.dismiss()
        }
        bindingBlood.tvBPositive.setOnClickListener {
            binding.edtBloodGroup.setText("B+")
            a1?.dismiss()
        }
        bindingBlood.tvOPositive.setOnClickListener {
            binding.edtBloodGroup.setText("O+")
            a1?.dismiss()
        }
        bindingBlood.tvANegative.setOnClickListener {
            binding.edtBloodGroup.setText("A-")
            a1?.dismiss()
        }
        bindingBlood.tvBNegative.setOnClickListener {
            binding.edtBloodGroup.setText("B-")
            a1?.dismiss()
        }
        bindingBlood.tvONegative.setOnClickListener {
            binding.edtBloodGroup.setText("O-")
            a1?.dismiss()
        }


        bindingBlood.ivClose.setOnClickListener {
            a1?.dismiss()
        }

        a1 = b1!!.create()
        a1!!.show()
    }


    private fun showDialogue() {
        val binding1 = DialogueThanksBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(
            this@RequestAddActivity.applicationContext, R.style.TransparentDialog
        )
        b1!!.setView(binding1.root)

        binding1.btnOk.setOnClickListener {

            binding.edtName.setText("")
            binding.edtMobile.setText("")
            binding.edtBloodGroup.setText("")
            binding.edtState.setText("")
            binding.edtCity.setText("")
            binding.edtArea.setText("")
            binding.edtAddress.setText("")
            binding.edtHospitalName.setText("")

            sendNextIntent()
            /*            MainActivity.binding.viewPager.currentItem = 0
                        MainActivity.binding.viewPager.adapter?.notifyDataSetChanged()
                        MainActivity.binding.viewPager.adapter?.notifyItemChanged(0)*/
            a1?.dismiss()
        }

        binding1.ivClose.setOnClickListener {
            binding.edtName.setText("")
            binding.edtMobile.setText("")
            binding.edtBloodGroup.setText("")
            binding.edtState.setText("")
            binding.edtCity.setText("")
            binding.edtArea.setText("")
            binding.edtAddress.setText("")
            binding.edtHospitalName.setText("")
            sendNextIntent()
            a1?.dismiss()
        }

        a1?.setOnDismissListener { sendNextIntent() }

        a1 = b1!!.create()
        a1!!.show()
    }

    private fun sendNextIntent() {
        val intent = Intent(applicationContext, RequestActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        sendNextIntent()
    }


}