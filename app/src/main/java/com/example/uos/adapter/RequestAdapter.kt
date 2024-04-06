package com.example.uos.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycartooncharacter.utils.HelperResizer
import com.example.uos.databinding.RowRequestBinding
import com.example.uos.model.Request
import java.text.SimpleDateFormat
import java.util.Locale

class RequestAdapter(
    private var arrRequestUser: ArrayList<Request>,
    var context: Context,
    var isShow: Int
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
        holder.binding.tvName.text = "" + user.name
        Log.e("TAG123456789", "onBindViewHolder: " + user.mobile)
        holder.binding.tvMobile.text = "" + user.mobile
        holder.binding.tvState.text = "" + user.state
        holder.binding.tvCity.text = "" + user.city
        holder.binding.tvArea.text = "" + user.area
        holder.binding.tvHospital.text = "" + user.hospital_name

        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss:aa", Locale.getDefault())

        // Parse input timestamp
        val date = inputFormat.parse(user.timestamp)

        // Format output timestamp
        val formattedTimestamp = outputFormat.format(date)
        holder.binding.tvTime.text = "" + formattedTimestamp

        HelperResizer.getHeightAndWidth(context)
        HelperResizer.setWidth(context, holder.binding.cvView, 1020)

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