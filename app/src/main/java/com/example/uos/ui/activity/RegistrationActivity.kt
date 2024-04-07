package com.example.uos.ui.activity

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.databinding.ActivityRegistrationBinding
import com.example.uos.databinding.DialogueBloodGroupBinding
import com.example.uos.utils.CustomProgressDialog
import com.example.uos.utils.SharedPreference
import com.example.uos.utils.Utils
import java.util.Calendar
import java.util.GregorianCalendar


class RegistrationActivity : BaseActivity() {
    lateinit var binding: ActivityRegistrationBinding
    lateinit var vm: ViewModel
    private var registerMap: HashMap<String, String> = HashMap()
    private var user_donor = "user"
    private lateinit var customProgressDialog: CustomProgressDialog
    var a1: AlertDialog? = null
    var b1: AlertDialog.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[ViewModel::class.java]
        customProgressDialog = CustomProgressDialog(this)

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
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
            }
        }

        binding.rbDonor.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                user_donor = "donor"
                binding.edtLastDonate.visibility = View.VISIBLE
            }
        }

        binding.btnRegister.setOnClickListener {
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
                Utils.showToast(applicationContext, "Please Enter Name")
            } else if (email.isEmpty()) {
                binding.edtEmail.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Email")
            } else if (mobile.isEmpty()) {
                binding.edtMobile.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Mobile")
            } else if (bloodGroup.isEmpty()) {
                binding.edtBloodGroup.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Blood Group")
            } else if (state.isEmpty()) {
                binding.edtState.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter State Name")
            } else if (city.isEmpty()) {
                binding.edtCity.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter City Name")
            } else if (area.isEmpty()) {
                binding.edtArea.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Area Name")
            } else if (address.isEmpty()) {
                binding.edtAddress.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Address Name")
            } else if (dob.isEmpty()) {
                binding.edtDob.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Date Of Birth")
            } else if (password.isEmpty()) {
                binding.edtPassword.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Password")

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

            vm.userRegister(registerMap).observe(RegistrationActivity@ this) { result ->
                Log.e("TAG123456789", "onCreate: " + result.message)
                Log.e("TAG123456789", "onCreate: " + result.error)
                if (result != null) {
                    if (result.error == "200") {
                        customProgressDialog.dismiss()

                        SharedPreference.saveUser(applicationContext, result.user)
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        Toast.makeText(applicationContext, "" + result.message, Toast.LENGTH_SHORT)
                            .show()
                    } else {

                        customProgressDialog.dismiss()
                        Toast.makeText(applicationContext, "" + result.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    customProgressDialog.dismiss()
                    Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }
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
        val dp = DatePickerDialog(applicationContext,R.style.TransparentDialog,
            { datePicker, Year, Month, Day -> binding.edtDob.setText(Day.toString() + "/" + (Month + 1) + "/" + Year) },
            year,
            month,
            day
        )
        dp.show()
    }

    private fun showBloodDialogue() {
        val bindingBlood = DialogueBloodGroupBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(this@RegistrationActivity, R.style.TransparentDialog)
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
}