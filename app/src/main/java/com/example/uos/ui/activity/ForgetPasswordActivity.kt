package com.example.uos.ui.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.databinding.ActivityForgetPasswordBinding
import com.example.uos.utils.SharedPreference
import com.example.uos.utils.Utils

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var binding: ActivityForgetPasswordBinding
    lateinit var vm: ViewModel
    private var forgetMap: HashMap<String, String> = HashMap()
    private lateinit var progressDialog: ProgressDialog
    var a1: AlertDialog? = null
    var b1: AlertDialog.Builder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[ViewModel::class.java]

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")

        binding.btnForget.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val dob = binding.edtDob.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty()) {
                binding.edtEmail.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Email")
            } else if (dob.isEmpty()) {
                binding.edtDob.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter DOB")
            } else if (password.isEmpty()) {
                binding.edtPassword.requestFocusFromTouch()
                Utils.showToast(applicationContext, "Please Enter Password")
            } else {
                progressDialog.show()

                forgetMap["email"] = email
                forgetMap["dob"] = dob
                forgetMap["password"] = password
                vm.forgetPassword(forgetMap).observe(this) { result ->
                    Log.e("TAG123456789", "onCreate: " + result.message)
                    Log.e("TAG123456789", "onCreate: " + result.error)

                    if (result != null) {
                        if (result.error == "200") {
                            progressDialog.dismiss()

                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            Utils.showToast(applicationContext, "" + result.message)

                        } else {
                            progressDialog.dismiss()
                            Utils.showToast(applicationContext, "" + result.message)

                        }
                    } else {
                        progressDialog.dismiss()
                        Utils.showToast(applicationContext, "Error")

                    }
                }
            }

        }
    }
}