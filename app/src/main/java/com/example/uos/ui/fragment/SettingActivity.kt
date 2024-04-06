package com.example.uos.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.databinding.ActivitySettingBinding
import com.example.uos.databinding.DeleteDialogueBinding
import com.example.uos.model.User
import com.example.uos.ui.activity.AboutActivity
import com.example.uos.ui.activity.BaseActivity
import com.example.uos.ui.activity.LoginActivity
import com.example.uos.ui.activity.MainActivity
import com.example.uos.ui.activity.PrivacyPolicyActivity
import com.example.uos.ui.activity.TermsConditionActivity
import com.example.uos.utils.SharedPreference
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener

class SettingActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingBinding

    private lateinit var progressDialog: ProgressDialog
    private var a1: AlertDialog? = null
    private var b1: AlertDialog.Builder? = null
    private var user: User? = null
    lateinit var vm: ViewModel
    private var deleteMap: HashMap<String, String> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[ViewModel::class.java]
        user = SharedPreference.getUser(this@SettingActivity)

        binding.tvAboutUS.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("activity","setting"))
            finish()
        }

        binding.swtNotification.isChecked = SharedPreference.isCheckNotification(applicationContext)

        binding.swtNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            SharedPreference.setNotification(applicationContext,isChecked)
        }

        binding.tvProfile.setOnClickListener {
            val intent = Intent(applicationContext,ProfileActivity::class.java)
            intent.putExtra("activity","setting")
            startActivity(intent)
            finish()
        }

        binding.tvPrivacyPolicy.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
            finish()
        }
        binding.tvTermsCondition.setOnClickListener {
            startActivity(Intent(this, TermsConditionActivity::class.java))
            finish()
        }

        binding.tvDeleteAccount.setOnClickListener {
            if (user != null) {
                deleteUser(user?.id, user?.email)
            }

        }

        binding.ivBack.setOnClickListener { backEvent() }

    }

    private fun backEvent() {
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun deleteUser(id: String?, email: String?) {
        deleteMap.clear()
        deleteMap["id"] = ""+id
        deleteMap["email"] = ""+email

        val bindingDelete = DeleteDialogueBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(this@SettingActivity, R.style.TransparentDialog)
        b1!!.setView(bindingDelete.root)

        bindingDelete.ivClose.setOnClickListener { a1?.dismiss() }

        bindingDelete.tvDelete.setOnClickListener {
            vm.deleteUser(deleteMap).observe(this) { result ->
                if (result != null) {
                    if (result.error == "200") {
                        a1?.dismiss()
                        SharedPreference.clearUser(this)
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        a1?.dismiss()
                    }
                } else {
                    a1?.dismiss()
                }
            }
        }
        a1 = b1!!.create()
        a1!!.show()


    }

    override fun onBackPressed() {
        backEvent()
    }


}