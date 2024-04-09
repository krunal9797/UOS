package com.krunal.uos.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.krunal.uos.R
import com.krunal.uos.Vm.ViewModel
import com.krunal.uos.databinding.ActivitySettingBinding
import com.krunal.uos.databinding.DeleteDialogueBinding
import com.krunal.uos.model.User
import com.krunal.uos.utils.CustomProgressDialog
import com.krunal.uos.utils.SharedPreference
import com.krunal.uos.utils.Utils

class SettingActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingBinding

    private lateinit var customProgressDialog: CustomProgressDialog
    private var a1: AlertDialog? = null
    private var b1: AlertDialog.Builder? = null
    private var user: User? = null
    lateinit var vm: com.krunal.uos.Vm.ViewModel
    private var deleteMap: HashMap<String, String> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[com.krunal.uos.Vm.ViewModel::class.java]
        user = SharedPreference.getUser(this@SettingActivity)

        customProgressDialog = CustomProgressDialog(this)

        binding.tvAboutUS.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java).putExtra("activity","setting"))
            finish()
        }

        binding.swtNotification.isChecked = SharedPreference.isCheckNotification(applicationContext)

        binding.swtNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            SharedPreference.setNotification(applicationContext,isChecked)
        }

        binding.tvProfile.setOnClickListener {
            val intent = Intent(applicationContext, ProfileActivity::class.java)
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

        binding.tvLanguage.setOnClickListener {
            startActivity(Intent(this, LanguageActivity::class.java).putExtra("from",2))
            finish()
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
            if (bindingDelete.edtConfirm.text.equals("Confirm") && bindingDelete.edtConfirm.text.equals("confirm") && bindingDelete.edtConfirm.text.equals("CONFIRM"))
            {
                customProgressDialog.show()
                vm.deleteUser(deleteMap).observe(this) { result ->
                    if (result != null) {
                        if (result.error == "200") {
                            a1?.dismiss()
                            customProgressDialog.dismiss()
                            SharedPreference.clearUser(this)
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            a1?.dismiss()
                            customProgressDialog.dismiss()
                        }
                    } else {
                        a1?.dismiss()
                        customProgressDialog.dismiss()
                    }
                }
            }
            else
            {

                Utils.showToast(this, getString(R.string.type_confirm_to_delete_account))
            }

        }
        a1 = b1!!.create()
        a1!!.show()


    }

    override fun onBackPressed() {
        backEvent()
    }


}