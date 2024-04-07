package com.example.uos.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.databinding.ActivityProfileBinding
import com.example.uos.databinding.DialogueBloodGroupBinding
import com.example.uos.model.User
import com.example.uos.utils.CustomProgressDialog
import com.example.uos.utils.SharedPreference
import com.example.uos.utils.Utils
import java.util.Calendar
import java.util.GregorianCalendar

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var vm: ViewModel
    private var registerMap: HashMap<String, String> = HashMap()
    private var user_donor = "user"
    private lateinit var customProgressDialog: CustomProgressDialog
    private var a1: AlertDialog? = null
    private var b1: AlertDialog.Builder? = null
    private var user: User? = null
    private var activity:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[ViewModel::class.java]
        user = SharedPreference.getUser(this@ProfileActivity)
        customProgressDialog = CustomProgressDialog(this)
        activity = intent.getStringExtra("activity").toString()

        binding.ivBack.setOnClickListener {
            backEvent()
        }

        binding.ivLogout.setOnClickListener {
            SharedPreference.clearUser(this)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        if (user != null) {
            val name = user!!.name
            binding.edtName.setText("" + name)
            val email = user!!.email
            binding.edtEmail.setText("" + email)
            val mobile = user!!.mobile
            binding.edtMobile.setText("" + mobile)
            val bloodGroup = user!!.bloodgroup
            binding.edtBloodGroup.setText("" + bloodGroup)
            val state = user!!.state
            binding.edtState.setText("" + state)
            val city = user!!.city
            binding.edtCity.setText("" + city)
            val area = user!!.area
            binding.edtArea.setText("" + area)
            val address = user!!.address
            binding.edtAddress.setText("" + address)
            val dob = user!!.dob
            binding.edtDob.setText("" + dob)
            val lastDonate = user!!.last_donate
            binding.edtLastDonate.setText("" + lastDonate)
            binding.edtPassword.setText("")

            Log.e("TAG123456789", "onCreate: "+user?.user_donor.toString())

            if (user?.user_donor.equals("user"))
            {
                binding.rbUser.isChecked =true
                binding.rbDonor.isChecked =false
                binding.tvLastDonate.visibility = View.GONE
                binding.edtLastDonate.visibility = View.GONE
            }
            else if (user?.user_donor.equals("donor"))
            {
                binding.rbUser.isChecked =false
                binding.rbDonor.isChecked =true

                binding.tvLastDonate.visibility = View.VISIBLE
                binding.edtLastDonate.visibility = View.VISIBLE
            }
            else
            {
                binding.rbUser.isChecked =false
                binding.rbDonor.isChecked =false

                binding.tvLastDonate.visibility = View.GONE
                binding.edtLastDonate.visibility = View.GONE
            }




        }

        binding.edtBloodGroup.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showBloodDialogue()
            }
            true
        }

        binding.edtDob.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                dobDialogue()
            }
            true
        }

        binding.rbUser.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                user_donor = "user"
                binding.edtLastDonate.visibility = View.GONE
                binding.tvLastDonate.visibility = View.GONE

            }
        }

        binding.rbDonor.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                user_donor = "donor"
                binding.edtLastDonate.visibility = View.VISIBLE
                binding.tvLastDonate.visibility = View.VISIBLE

            }
        }

        binding.btnUpdate.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val mobile = binding.edtMobile.text.toString()
            val bloodGroup = binding.edtBloodGroup.text.toString()
            val state = binding.edtState.text.toString()
            val city = binding.edtCity.text.toString()
            val area = binding.edtArea.text.toString()
            val address = binding.edtAddress.text.toString()
            val dob = binding.edtDob.text.toString()
            val lastDonate = binding.edtLastDonate.text.toString()
            val password = binding.edtPassword.text.toString()

            if (name.isEmpty()) {
                binding.edtName.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter Name")
            } else if (email.isEmpty()) {
                binding.edtEmail.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter Email")
            } else if (mobile.isEmpty()) {
                binding.edtMobile.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter Mobile")
            } else if (bloodGroup.isEmpty()) {
                binding.edtBloodGroup.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter Blood Group")
            } else if (state.isEmpty()) {
                binding.edtState.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter State Name")
            } else if (city.isEmpty()) {
                binding.edtCity.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter City Name")
            } else if (area.isEmpty()) {
                binding.edtArea.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter Area Name")
            } else if (address.isEmpty()) {
                binding.edtAddress.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter Address Name")
            } else if (dob.isEmpty()) {
                binding.edtDob.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter Date Of Birth")
            } else if (password.isEmpty()) {
                binding.edtPassword.requestFocusFromTouch()
                Utils.showToast(this, "Please Enter New Password")

            } else
                customProgressDialog.show()

            registerMap["name"] = name
            registerMap["email"] = email
            registerMap["mobile"] = mobile
            registerMap["bloodgroup"] = bloodGroup
            registerMap["state"] = state
            registerMap["city"] = city
            registerMap["area"] = area
            registerMap["address"] = address
            registerMap["dob"] = dob
            registerMap["lastDonate"] = lastDonate
            registerMap["password"] = password
            registerMap["user_donor"] = user_donor

            vm.updateUser(registerMap).observe(this) { result ->
                Log.e("TAG123456789", "onCreate: " + result.message)
                Log.e("TAG123456789", "onCreate: " + result.error)
                if (result != null) {
                    if (result.error == "200") {
                        customProgressDialog.dismiss()
                        SharedPreference.saveUser(this, result.user)
                        Utils.showToast(this,"Profile Update Successfully")
                        //loadFragment(SettingFragment())
                    } else {
                        customProgressDialog.dismiss()
                        Utils.showToast(this, "" + result.message)
                    }
                } else {
                    customProgressDialog.dismiss()
                    Utils.showToast(this, "An error occurred")
                }
            }
        }


    }

    private fun backEvent() {
        if (activity!=null && activity == "setting")
        {
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
            finish()
        }
        else{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun dobDialogue() {
        val gc1 = GregorianCalendar()
        val year: Int
        val month: Int
        val day: Int
        year = gc1.get(Calendar.YEAR)
        month = gc1.get(Calendar.MONTH)
        day = gc1.get(Calendar.DAY_OF_MONTH)
        val dp = DatePickerDialog(
            this, R.style.TransparentDialog,
            { datePicker, Year, Month, Day -> binding.edtDob.setText(Day.toString() + "/" + (Month + 1) + "/" + Year) },
            year,
            month,
            day
        )
        dp.show()
    }

    private fun showBloodDialogue() {
        val bindingBlood = DialogueBloodGroupBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(this@ProfileActivity, R.style.TransparentDialog)
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

    override fun onBackPressed() {
        backEvent()
    }
}