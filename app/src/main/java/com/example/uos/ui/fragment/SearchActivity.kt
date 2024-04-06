package com.example.uos.ui.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.adapter.SearchAdapter
import com.example.uos.databinding.ActivitySearchBinding
import com.example.uos.databinding.DialogueBloodGroupBinding
import com.example.uos.interfaces.ClickToCall
import com.example.uos.model.User
import com.example.uos.ui.activity.BaseActivity
import com.example.uos.ui.activity.MainActivity
import com.example.uos.utils.SharedPreference
import com.example.uos.utils.Utils

class SearchActivity : BaseActivity(), ClickToCall {

    private lateinit var binding: ActivitySearchBinding
    lateinit var vm: ViewModel
    private var registerMap: HashMap<String, String> = HashMap()
    private var callMap: HashMap<String, String> = HashMap()
    private lateinit var progressDialog: ProgressDialog
    lateinit var searchAdapter: SearchAdapter
    private var arrUser: ArrayList<User> = ArrayList()
    private var user: User? = null
    var a1: AlertDialog? = null
    var b1: AlertDialog.Builder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[ViewModel::class.java]
        user = SharedPreference.getUser(this@SearchActivity)

        setEvent()
    }

    private fun setEvent() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")

        searchAdapter = SearchAdapter(arrUser, this, this@SearchActivity)
        binding.rvSearch.adapter = searchAdapter

        binding.edtBloodGroup.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {

                val imm =
                    v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                showBloodDialogue()
            }
            true
        }

        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                // Handle the search action here
                if (binding.edtBloodGroup.text.toString().equals("")) {
                    binding.edtBloodGroup.requestFocusFromTouch()
                    showBloodDialogue()
                    Utils.showToast(this, "Select Blood Group")
                }
                /*                else
                                {
                                    val searchText = binding.edtSearch.text.toString().trim()
                                    callSearchApiBloodGroup(searchText)
                                }*/

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.ivBack.setOnClickListener {
            backEvent()
        }

    }

    private fun backEvent() {
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun callSearchApiBloodGroup(s: String) {
        progressDialog.show()
        registerMap.clear()
        registerMap["bloodgroup"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    progressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                    arrUser.clear()
                    arrUser.addAll(result.users)
                    searchAdapter.notifyDataSetChanged()
                    if (arrUser.isNotEmpty()) {
                        searchAdapter.filterByBloodGroup(binding.edtBloodGroup.text.toString())
                    }
                } else {

                    callSearchApiState(s)

                }
            } else {
                progressDialog.dismiss()
                Utils.showToast(this, "An error occurred")
            }
        }
    }

    private fun callSearchApiState(s: String) {
        progressDialog.show()
        registerMap.clear()
        registerMap["state"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    progressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                    arrUser.clear()
                    arrUser.addAll(result.users)
                    searchAdapter.notifyDataSetChanged()
                    if (arrUser.isNotEmpty()) {
                        searchAdapter.filterByBloodGroup(binding.edtBloodGroup.text.toString())
                    }
                } else {

                    callSearchApiCity(s)
                }
            } else {
                progressDialog.dismiss()
                Utils.showToast(this, "An error occurred")
            }
        }
    }

    private fun callSearchApiCity(s: String) {
        progressDialog.show()
        registerMap.clear()
        registerMap["city"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    progressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                    arrUser.clear()
                    arrUser.addAll(result.users)
                    searchAdapter.notifyDataSetChanged()
                    if (arrUser.isNotEmpty()) {
                        searchAdapter.filterByBloodGroup(binding.edtBloodGroup.text.toString())
                    }
                } else {

                    callSearchApiArea(s)
                }
            } else {
                progressDialog.dismiss()
                Utils.showToast(this, "An error occurred")
            }
        }
    }

    private fun callSearchApiArea(s: String) {
        progressDialog.show()
        registerMap.clear()
        registerMap["area"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    progressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                    arrUser.clear()
                    arrUser.addAll(result.users)
                    searchAdapter.notifyDataSetChanged()
                    if (arrUser.isNotEmpty()) {
                        searchAdapter.filterByBloodGroup(binding.edtBloodGroup.text.toString())
                    }
                } else {

                    callSearchApiAddress(s)
                }
            } else {
                progressDialog.dismiss()
                Utils.showToast(this, "An error occurred")
            }
        }
    }

    private fun callSearchApiAddress(s: String) {
        progressDialog.show()
        registerMap.clear()
        registerMap["address"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    progressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                    arrUser.clear()
                    arrUser.addAll(result.users)
                    searchAdapter.notifyDataSetChanged()
                    if (arrUser.isNotEmpty()) {
                        searchAdapter.filterByBloodGroup(binding.edtBloodGroup.text.toString())
                    }
                } else {
                    progressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)

                }
            } else {
                progressDialog.dismiss()
                Utils.showToast(this, "An error occurred")
            }
        }
    }

    private fun showBloodDialogue() {

        val bindingBlood = DialogueBloodGroupBinding.inflate(layoutInflater)
        b1 = AlertDialog.Builder(this, R.style.TransparentDialog)
        b1!!.setView(bindingBlood.root)

        bindingBlood.tvAPositive.setOnClickListener {
            binding.edtBloodGroup.setText("A+")
            CallApi()
            a1?.dismiss()
        }
        bindingBlood.tvBPositive.setOnClickListener {
            binding.edtBloodGroup.setText("B+")
            CallApi()
            a1?.dismiss()
        }
        bindingBlood.tvOPositive.setOnClickListener {
            binding.edtBloodGroup.setText("O+")
            CallApi()
            a1?.dismiss()
        }
        bindingBlood.tvANegative.setOnClickListener {
            binding.edtBloodGroup.setText("A-")
            CallApi()
            a1?.dismiss()
        }
        bindingBlood.tvBNegative.setOnClickListener {
            binding.edtBloodGroup.setText("B-")
            CallApi()
            a1?.dismiss()
        }
        bindingBlood.tvONegative.setOnClickListener {
            binding.edtBloodGroup.setText("O-")
            CallApi()
            a1?.dismiss()
        }

        bindingBlood.ivClose.setOnClickListener {
            a1?.dismiss()
        }

        a1 = b1!!.create()
        a1!!.show()
    }

    private fun CallApi() {
        val searchText = binding.edtSearch.text.toString().trim()
        callSearchApiBloodGroup(searchText)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.edtSearch.text?.clear()
        binding.edtBloodGroup.text?.clear()
    }

    override fun onClickCall(userD: User) {
        if (!userD.mobile.isNullOrEmpty()) {
            insertData(userD.mobile, userD.id)

        } else {
            // Handle case where mobile number is not available
            Utils.showToast(this, "Mobile number not available")
        }
    }

    private fun insertData(mobile: String, id: String) {
        progressDialog.show()
        callMap.clear()
        callMap["uid"] = user?.id.toString()
        callMap["did"] = id

        vm.callInsert(callMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    progressDialog.dismiss()

                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${mobile}")
                    }
                    startActivity(intent)

                } else {
                    progressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                }
            } else {
                progressDialog.dismiss()
                Utils.showToast(this, "An error occurred")
            }
        }
    }

    override fun onBackPressed() {
        backEvent()
    }


}