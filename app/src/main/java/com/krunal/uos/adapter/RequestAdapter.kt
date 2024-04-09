package com.krunal.uos.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycartooncharacter.utils.HelperResizer
import com.krunal.uos.R
import com.krunal.uos.databinding.RowRequestBinding
import com.krunal.uos.interfaces.ClickToCall
import com.krunal.uos.interfaces.ClickToRequest
import com.krunal.uos.model.Request
import com.krunal.uos.utils.Utils
import java.text.SimpleDateFormat
import java.util.Locale

class RequestAdapter(
    private var arrRequestUser: ArrayList<Request>,
    var context: Context,
    var isShow: Int,
    var clickToRequest: ClickToRequest
) :
    RecyclerView.Adapter<RequestAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowRequestBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = arrRequestUser[position]

        holder.binding.tvBloodGroup.text = "" + user.bloodgroup
        holder.binding.tvName.text = context.getString(R.string.name_) + user.name
        Log.e("TAG123456789", "onBindViewHolder: " + user.mobile)
        holder.binding.tvMobile.text = context.getString(R.string.mobile_) + user.mobile
        holder.binding.tvState.text = context.getString(R.string.state_) + user.state
        holder.binding.tvCity.text = context.getString(R.string.city_) + user.city
        holder.binding.tvArea.text = context.getString(R.string.area_) + user.area
        holder.binding.tvAddress.text = context.getString(R.string.address_) + user.address
        holder.binding.tvHospital.text = context.getString(R.string.hospital_) + user.hospital_name

        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss:aa", Locale.getDefault())

        // Parse input timestamp
        val date = inputFormat.parse(user.timestamp)

        // Format output timestamp
        val formattedTimestamp = outputFormat.format(date)
        holder.binding.tvTime.text = ""+context.getString(R.string.date_time) + formattedTimestamp

        holder.binding.ivCall.setOnClickListener {
            clickToRequest.onClickCall(user)
        }

        holder.binding.ivLocation.setOnClickListener {
            val address = user.address
            if (!address.isNullOrEmpty()) {
                val mapIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
                val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                if (mapIntent.resolveActivity(holder.itemView.context.packageManager) != null) {
                    // Google Maps app exists, open the location directly
                    holder.itemView.context.startActivity(mapIntent)
                } else {
                    // Google Maps app does not exist, open a web view
                    val webViewIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=${Uri.encode(address)}"))
                    holder.itemView.context.startActivity(webViewIntent)
                }
            } else {
                // Handle case where address is not available
                Utils.showToast(context, context.getString(R.string.address_not_found))
            }
        }

    }

    override fun getItemCount(): Int {
        return if (isShow == 0) {
            if (arrRequestUser.size > 0) {
                1
            } else {
                arrRequestUser.size
            }
        } else {
            arrRequestUser.size
        }
    }
}