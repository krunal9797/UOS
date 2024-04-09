package com.krunal.uos.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.krunal.uos.R
import com.krunal.uos.Vm.ViewModel
import com.krunal.uos.adapter.SearchAdapter
import com.krunal.uos.databinding.ActivitySearchBinding
import com.krunal.uos.databinding.DialogueBloodGroupBinding
import com.krunal.uos.interfaces.ClickToCall
import com.krunal.uos.model.User
import com.krunal.uos.utils.CustomProgressDialog
import com.krunal.uos.utils.SharedPreference
import com.krunal.uos.utils.Utils

class SearchActivity : BaseActivity(), ClickToCall {

    private lateinit var binding: ActivitySearchBinding
    lateinit var vm: com.krunal.uos.Vm.ViewModel
    private var registerMap: HashMap<String, String> = HashMap()
    private var callMap: HashMap<String, String> = HashMap()
    private lateinit var customProgressDialog: CustomProgressDialog
    private lateinit var searchAdapter: SearchAdapter
    private var arrUser: ArrayList<User> = ArrayList()
    private var user: User? = null
    var a1: AlertDialog? = null
    var b1: AlertDialog.Builder? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm = ViewModelProvider(this)[com.krunal.uos.Vm.ViewModel::class.java]
        user = SharedPreference.getUser(this@SearchActivity)
        customProgressDialog = CustomProgressDialog(this)

        setEvent()
        CallApi()
    }

    private fun setEvent() {
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
                    Utils.showToast(this, ""+getString(R.string.choose_blood_group))
                } else {
                    val searchText = binding.edtSearch.text.toString().trim()
                    callSearchApiBloodGroup(searchText)
                }

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
        customProgressDialog.show()
        registerMap.clear()
        registerMap["bloodgroup"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    customProgressDialog.dismiss()
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
                customProgressDialog.dismiss()
                Utils.showToast(this,""+getString(R.string.an_error_occurred))
            }
        }
    }

    private fun callSearchApiState(s: String) {
        customProgressDialog.show()
        registerMap.clear()
        registerMap["state"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    customProgressDialog.dismiss()
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
                customProgressDialog.dismiss()
                Utils.showToast(this,""+getString(R.string.an_error_occurred))
            }
        }
    }

    private fun callSearchApiCity(s: String) {
        customProgressDialog.show()
        registerMap.clear()
        registerMap["city"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    customProgressDialog.dismiss()
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
                customProgressDialog.dismiss()
                Utils.showToast(this,""+getString(R.string.an_error_occurred))
            }
        }
    }

    private fun callSearchApiArea(s: String) {
        customProgressDialog.show()
        registerMap.clear()
        registerMap["area"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    customProgressDialog.dismiss()
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
                customProgressDialog.dismiss()
                Utils.showToast(this,""+getString(R.string.an_error_occurred))
            }
        }
    }

    private fun callSearchApiAddress(s: String) {
        customProgressDialog.show()
        registerMap.clear()
        registerMap["address"] = s.toString()

        vm.getSearch(registerMap).observe(this) { result ->
            if (result != null) {
                if (result.error == "200") {
                    customProgressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)
                    arrUser.clear()
                    arrUser.addAll(result.users)
                    searchAdapter.notifyDataSetChanged()
                    if (arrUser.isNotEmpty()) {
                        searchAdapter.filterByBloodGroup(binding.edtBloodGroup.text.toString())
                    }
                } else {
                    customProgressDialog.dismiss()
                    Utils.showToast(this, "" + result.message)

                }
            } else {
                customProgressDialog.dismiss()
                Utils.showToast(this,""+getString(R.string.an_error_occurred))
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
            Utils.showToast(this, ""+getString(R.string.mobile_number_not_available))
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
                Utils.showToast(this,""+getString(R.string.an_error_occurred))
            }
        }
    }

    override fun onBackPressed() {
        backEvent()
    }


}