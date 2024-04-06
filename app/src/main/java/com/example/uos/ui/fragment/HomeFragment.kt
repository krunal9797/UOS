package com.example.uos.ui.fragment

import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.uos.R
import com.example.uos.Vm.ViewModel
import com.example.uos.adapter.GalleryAdapter
import com.example.uos.adapter.RequestAdapter
import com.example.uos.databinding.DonateDialogueBinding
import com.example.uos.databinding.FragmentHomeBinding
import com.example.uos.model.Gallery
import com.example.uos.model.Request
import com.example.uos.model.User
import com.example.uos.ui.activity.GalleryActivity
import com.example.uos.ui.activity.RequestActivity
import com.example.uos.utils.SharedPreference
import java.util.Calendar


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var vm: ViewModel

    private lateinit var requestAdapter: RequestAdapter
    private lateinit var galleryAdapter: GalleryAdapter
    private var user: User? = null
    val handler = Handler()
    val delay = 10000L // 3 seconds delay

    private var a1: AlertDialog? = null
    private var b1: AlertDialog.Builder? = null

    companion object{
        private var arrGallery: ArrayList<Gallery> = ArrayList()
        private var arrRequestUser: ArrayList<Request> = ArrayList()
    }

    private val shuffleRunnable = object : Runnable {
        override fun run() {
            if (arrGallery.isNotEmpty())
            {
                // Shuffle the arrGallery list
                arrGallery.shuffle()

                // Notify the adapter of the data change
                galleryAdapter.notifyDataSetChanged()

                // Post the same runnable again after the delay
                handler.postDelayed(this, delay)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        vm = ViewModelProvider(this)[ViewModel::class.java]
        user = SharedPreference.getUser(this@HomeFragment.requireContext())

        requestAdapter = RequestAdapter(arrRequestUser,requireContext(),0)
        binding.rvRequest.adapter = requestAdapter




        getGallery()


        getData()


        val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greetingMessage = getGreetingMessage(currentTime)
        binding.tvCurrentTime.text = ""+greetingMessage

        binding.tvSeeMore.setOnClickListener {
            startActivity(Intent(requireContext(),RequestActivity::class.java))
        }

        if (arrRequestUser.isNotEmpty())
        {
            requestAdapter.notifyDataSetChanged()
        }
        else
        {
            vm.getAllRequest().observe(requireActivity()) { result ->
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

        binding.tvSeeMoreGallery.setOnClickListener {
            startActivity(Intent(requireContext(),GalleryActivity::class.java))
        }

       /* binding.rlBloodbank.setOnClickListener {
            val mapIntentUri = Uri.parse("geo:0,0?q=near by blood bank")
            val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                // Google Maps app is installed, open it
                startActivity(mapIntent)
            } else {
                // Google Maps app is not installed, open a web browser
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=near+by+blood+bank"))
                startActivity(webIntent)
            }
        }

        binding.rlHospital.setOnClickListener {
            val mapIntentUri = Uri.parse("geo:0,0?q=near by hospital")
            val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                // Google Maps app is installed, open it
                startActivity(mapIntent)
            } else {
                // Google Maps app is not installed, open a web browser
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=near+by+hospital"))
                startActivity(webIntent)
            }

        }*/

        binding.rlDonate.setOnClickListener {
            val bindingBlood = DonateDialogueBinding.inflate(layoutInflater)
            b1 = AlertDialog.Builder(this@HomeFragment.requireContext(), R.style.TransparentDialog)
            b1!!.setView(bindingBlood.root)

            bindingBlood.tvCall.setOnClickListener {
                val phoneNumber = "1234567890"
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:$phoneNumber")
                startActivity(callIntent)
                a1?.dismiss() // Dismiss the dialog after initiating the call
            }

            bindingBlood.tvWhatsapp.setOnClickListener {
                val phoneNumber = "+917046114313"
                val whatsappIntent = Intent(Intent.ACTION_VIEW)
                whatsappIntent.data = Uri.parse("https://wa.me/$phoneNumber")
                startActivity(whatsappIntent)
                a1?.dismiss() // Dismiss the dialog after redirecting to WhatsApp
            }

            a1 = b1!!.create()
            a1!!.show()
        }

    }

    private fun getData() {
        vm.getDataChange().observe(requireActivity()){
            Log.e("TAG", "getData: "+it.message )
            if (it.error == "200"){
                it.data.forEach {
                    if(it.name.equals("Camp"))
                    {
                        //binding.tvTotalCamp.setText(""+it.uos_data)
                        val campCount = it.uos_data.toInt()
                        val animator = ValueAnimator.ofInt(0, campCount)
                        animator.setDuration(5000) // 5000 milliseconds (5 seconds)

                        animator.addUpdateListener { valueAnimator ->
                            val animatedValue = valueAnimator.getAnimatedValue() as Int
                            binding.tvTotalCamp.text = animatedValue.toString()
                        }
                        animator.start()
                    }
                    else if (it.name.equals("Blood Unit"))
                    {

                        val unitcount = it.uos_data.toInt()
                        val animator = ValueAnimator.ofInt(0, unitcount)
                        animator.setDuration(5000) // 5000 milliseconds (5 seconds)

                        animator.addUpdateListener { valueAnimator ->
                            val animatedValue = valueAnimator.getAnimatedValue() as Int
                            binding.tvTotalUnit.text = animatedValue.toString()
                        }
                        animator.start()
                    }
                    else if (it.name.equals("Total Funding"))
                    {
                        val totalFund = it.uos_data.toInt()
                        val animator = ValueAnimator.ofInt(0, totalFund)
                        animator.setDuration(5000) // 5000 milliseconds (5 seconds)

                        animator.addUpdateListener { valueAnimator ->
                            val animatedValue = valueAnimator.getAnimatedValue() as Int
                            binding.tvTotlaDonation.text = animatedValue.toString()
                        }
                        animator.start()
                     }
                }
            }
        }
    }

    private fun getGallery() {
        vm.getGallery().observe(requireActivity()){result ->
            Log.e("TAG123456789", "onCreate: " + result.message)
            if (result.error == "200")
            {
                arrGallery.clear()
                arrGallery.addAll(result.gallery.reversed())
                galleryAdapter.notifyDataSetChanged()
                handler.postDelayed(shuffleRunnable, delay)
            }
        }
    }

    private fun getGreetingMessage(hourOfDay: Int): String {
        return when (hourOfDay) {
            in 0..4 -> "Good Night"
            in 5..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            in 17..20 -> "Good Evening"
            else -> "Welcome To UOS"
        }
    }
}