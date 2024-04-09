package com.krunal.uos.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.lifecycle.ViewModelProvider
import com.krunal.uos.databinding.ActivityRequestBinding
import com.krunal.uos.R
import com.krunal.uos.Vm.ViewModel
import com.krunal.uos.adapter.RequestAdapter
import com.krunal.uos.interfaces.ClickToRequest
import com.krunal.uos.model.Request
import com.krunal.uos.model.User
import com.krunal.uos.utils.CustomProgressDialog
import com.krunal.uos.utils.SharedPreference
import com.krunal.uos.utils.Utils

class RequestActivity : BaseActivity(), ClickToRequest {
    private lateinit var binding: ActivityRequestBinding
    lateinit var vm: com.krunal.uos.Vm.ViewModel
    private lateinit var customProgressDialog: CustomProgressDialog
    private var callMap: HashMap<String, String> = HashMap()

    companion object {
        private var arrRequestUser: ArrayList<Request> = ArrayList()
    }

    private lateinit var requestAdapter: RequestAdapter
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this)[com.krunal.uos.Vm.ViewModel::class.java]

        user = SharedPreference.getUser(applicationContext)
        customProgressDialog = CustomProgressDialog(this)
        customProgressDialog.show()
        requestAdapter = RequestAdapter(arrRequestUser, applicationContext, 1, this)
        binding.rvRequest.adapter = requestAdapter

        if (arrRequestUser.isNotEmpty()) {
            customProgressDialog.dismiss()
            requestAdapter.notifyDataSetChanged()
        } else {
            vm.getAllRequest().observe(this@RequestActivity) { result ->
                Log.e("TAG123456789", "onCreate: " + result.message)
                Log.e("TAG123456789", "onCreate: " + result.error)
                if (result != null) {
                    if (result.error == "200") {
                        arrRequestUser.clear()
                        arrRequestUser.addAll(result.requests)
                        requestAdapter.notifyDataSetChanged()
                        customProgressDialog.dismiss()
                    } else {
                        customProgressDialog.dismiss()
                    }
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(applicationContext, RequestAddActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.ivBack.setOnClickListener { backEvent() }

        rotateImageView()
    }

    private fun rotateImageView() {
        val rotateAnimation = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 2000 // 30 seconds
            repeatCount = Animation.INFINITE // Repeat indefinitely
        }

        binding.fabAdd.startAnimation(rotateAnimation)
    }

    private fun backEvent() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        backEvent()
    }

    override fun onClickCall(userR: Request) {
        if (!userR.mobile.isNullOrEmpty()) {
            insertData(userR.mobile, userR.id)

        } else {
            // Handle case where mobile number is not available
            Utils.showToast(this, getString(R.string.mobile_number_not_available))
        }
    }

    private fun insertData(mobile: String, id: String) {
        customProgressDialog.show()
        callMap.clear()
        callMap["uid"] = user?.id.toString()
        callMap["did"] = id

        vm.callInsert(callMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    customProgressDialog.dismiss()

                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${mobile}")
                    }
                    startActivity(intent)

                } else {
                    customProgressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                }
            } else {
                customProgressDialog.dismiss()
                Utils.showToast(this, ""+getString(R.string.an_error_occurred))
            }
        }
    }
}