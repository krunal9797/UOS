package com.example.uos.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.databinding.ActivityLoginBinding
import com.example.uos.utils.SharedPreference
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class LoginActivity : BaseActivity() {
    lateinit var binding:ActivityLoginBinding
    var isEmailVisible = true

    lateinit var vm: ViewModel
    lateinit var mAdView:AdView;
    private var registerMap: HashMap<String, String> = HashMap()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[ViewModel::class.java]

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")

        binding.tvLoginWithEmail.setOnClickListener {
            if (!isEmailVisible) {
                animateFlip()
                isEmailVisible = true
            }
        }

        binding.tvLoginWithMobile.setOnClickListener {
            if (isEmailVisible) {
                animateFlip()
                isEmailVisible = false
            }
        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(applicationContext,RegistrationActivity::class.java))
            finish()

        }

        binding.tvForgotPass.setOnClickListener {
            startActivity(Intent(applicationContext,ForgetPasswordActivity::class.java))
            finish()
        }


        binding.btnLogin.setOnClickListener {
            progressDialog.show()
            val email = binding.edtEmail.text.toString()
            val mobile = binding.edtMobile.text.toString()
            val password = binding.edtPassword.text.toString()


            if (isEmailVisible) {
                if (email.isEmpty()) {
                    binding.edtEmail.requestFocusFromTouch()
                } else if (password.isEmpty()) {
                    binding.edtPassword.requestFocusFromTouch()
                } else {
                    registerMap.clear()
                    registerMap["email"] = email
                    registerMap["password"] = password

                    callApi(registerMap)

                }
            } else {
                if (mobile.isEmpty()) {
                    binding.edtMobile.requestFocusFromTouch()
                } else if (password.isEmpty()) {
                    binding.edtPassword.requestFocusFromTouch()
                } else {
                    registerMap.clear()
                    registerMap["mobile"] = mobile
                    registerMap["password"] = password
                    callApi(registerMap)
                }
            }

        }

    }

    private fun callApi(registerMap: HashMap<String, String>) {

        vm.getLogin(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    progressDialog.dismiss()

                    Toast.makeText(
                        applicationContext, "" + result.user.name, Toast.LENGTH_SHORT
                    ).show()
                    SharedPreference.saveUser(applicationContext,result.user)
                    val intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    progressDialog.dismiss()

                    Toast.makeText(
                        applicationContext, "" + result.message, Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                progressDialog.dismiss()
                Toast.makeText(
                    applicationContext, "An error occurred", Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun animateFlip() {
        if (isEmailVisible) {
            binding.tvEmail.visibility = View.VISIBLE
            binding.edtEmail.visibility = View.VISIBLE
            binding.tvMobile.visibility = View.GONE
            binding.edtMobile.visibility = View.GONE
            binding.tvLoginWithMobile.visibility = View.VISIBLE
            binding.tvLoginWithEmail.visibility = View.GONE
        } else {
            binding.edtEmail.visibility = View.GONE
            binding.tvEmail.visibility = View.GONE
            binding.edtMobile.visibility = View.VISIBLE
            binding.tvMobile.visibility = View.VISIBLE
            binding.tvLoginWithMobile.visibility = View.GONE
            binding.tvLoginWithEmail.visibility = View.VISIBLE
        }

        binding.edtEmail.setText("")
        binding.edtMobile.setText("")
        binding.edtPassword.setText("")
    }


    override fun onStart() {
        super.onStart()
        val isLoggedIn = SharedPreference.isLoggedIn(applicationContext)
        if (isLoggedIn) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}