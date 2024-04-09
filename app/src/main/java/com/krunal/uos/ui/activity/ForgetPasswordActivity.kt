package com.krunal.uos.ui.activity

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.krunal.uos.R
import com.krunal.uos.Vm.ViewModel
import com.krunal.uos.databinding.ActivityForgetPasswordBinding
import com.krunal.uos.utils.CustomProgressDialog
import com.krunal.uos.utils.SharedPreference
import com.krunal.uos.utils.Utils
import java.util.Calendar
import java.util.GregorianCalendar

class ForgetPasswordActivity : BaseActivity() {
    lateinit var binding: ActivityForgetPasswordBinding
    lateinit var vm: com.krunal.uos.Vm.ViewModel
    private var forgetMap: HashMap<String, String> = HashMap()
    private lateinit var customProgressDialog: CustomProgressDialog
    var a1: AlertDialog? = null
    var b1: AlertDialog.Builder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[com.krunal.uos.Vm.ViewModel::class.java]
        customProgressDialog = CustomProgressDialog(this)

        binding.edtDob.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                dobDialogue()
            }
            true
        }


        binding.btnForget.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val dob = binding.edtDob.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty()) {
                binding.edtEmail.requestFocusFromTouch()
                Utils.showToast(this, getString(R.string.please_enter_email))
            } else if (dob.isEmpty()) {
                binding.edtDob.requestFocusFromTouch()
                Utils.showToast(this, getString(R.string.please_enter_dob))
            } else if (password.isEmpty()) {
                binding.edtPassword.requestFocusFromTouch()
                Utils.showToast(this, getString(R.string.please_enter_password))
            } else {
                customProgressDialog.show()

                forgetMap["email"] = email
                forgetMap["dob"] = dob
                forgetMap["password"] = password
                vm.forgetPassword(forgetMap).observe(this) { result ->
                    Log.e("TAG123456789", "onCreate: " + result.message)
                    Log.e("TAG123456789", "onCreate: " + result.error)

                    if (result != null) {
                        if (result.error == "200") {
                            customProgressDialog.dismiss()
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            customProgressDialog.dismiss()
                            Utils.showToast(applicationContext, "" + result.message)

                        }
                    } else {
                        customProgressDialog.dismiss()
                        Utils.showToast(applicationContext, getString(R.string.an_error_occurred))

                    }
                }
            }

        }

        binding.ivBack.setOnClickListener {
            backEvent()
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
        val dp = DatePickerDialog(this,R.style.CustomDatePickerDialogStyle,
            { datePicker, Year, Month, Day -> binding.edtDob.setText(Day.toString() + "/" + (Month + 1) + "/" + Year) },
            year,
            month,
            day
        )
        dp.show()
    }

    override fun onBackPressed() {
        backEvent()
    }

    private fun backEvent() {
        val intent = Intent(applicationContext,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}