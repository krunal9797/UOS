package com.example.uos.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.adapter.RequestAdapter
import com.example.uos.databinding.ActivityRequestBinding
import com.example.uos.model.Request
import com.example.uos.model.User
import com.example.uos.ui.fragment.RequestAddActivity
import com.example.uos.utils.SharedPreference

class RequestActivity : BaseActivity() {
    private lateinit var binding:ActivityRequestBinding
    lateinit var vm: ViewModel
    companion object{
        private var arrRequestUser: ArrayList<Request> = ArrayList()
    }
    private lateinit var requestAdapter: RequestAdapter
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vm = ViewModelProvider(this)[ViewModel::class.java]

        user = SharedPreference.getUser(applicationContext)

        requestAdapter = RequestAdapter(arrRequestUser,applicationContext,1)
        binding.rvRequest.adapter = requestAdapter

        if (arrRequestUser.isNotEmpty())
        {
            requestAdapter.notifyDataSetChanged()
        }
        else
        {
            vm.getAllRequest().observe(this@RequestActivity) { result ->
                Log.e("TAG123456789", "onCreate: " + result.message)
                Log.e("TAG123456789", "onCreate: " + result.error)
                if (result != null) {
                    if (result.error == "200") {
                        arrRequestUser.clear()
                        arrRequestUser.addAll(result.requests)
                        requestAdapter.notifyDataSetChanged()
                    }
                    else{

                    }
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(applicationContext,RequestAddActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.ivBack.setOnClickListener { backEvent() }

    }

    private fun backEvent() {
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        backEvent()
    }
}