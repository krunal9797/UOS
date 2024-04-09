package com.krunal.uos.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krunal.uos.R
import com.krunal.uos.databinding.RowUserBinding
import com.krunal.uos.interfaces.ClickToCall
import com.krunal.uos.model.User
import com.krunal.uos.utils.Utils

class SearchAdapter(private var arrUser: ArrayList<User>, var context: Context,var clickToCall: ClickToCall) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private var filteredList: ArrayList<User> = ArrayList()

    init {
        // Initialize filteredList with arrUser to avoid modifying the original list
        filteredList.addAll(arrUser)
    }

    // Function to filter data by blood group
    fun filterByBloodGroup(bloodGroup: String) {
        filteredList.clear()
        for (user in arrUser) {
            if (user.bloodgroup == bloodGroup) {
                filteredList.add(user)
            }
        }
        notifyDataSetChanged()
    }

    fun setData(filteredUsers: ArrayList<User>) {
        arrUser.clear()
        arrUser.addAll(filteredUsers)
        notifyDataSetChanged()
    }
    companion object {
        private const val AD_TYPE = 2
        private const val CONTENT_TYPE = 1
    }

    class MyViewHolder(val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = filteredList[position]

        holder.binding.tvBloodGroup.text = ""+user.bloodgroup

        holder.binding.tvName.text = context.getString(R.string.name_) + user.name
        holder.binding.tvMobile.text = context.getString(R.string.mobile_) + user.mobile
        holder.binding.tvState.text = context.getString(R.string.state_) + user.state
        holder.binding.tvCity.text = context.getString(R.string.city_) + user.city
        holder.binding.tvArea.text = context.getString(R.string.area_) + user.area


        Log.e("TAG123456789", "onBindViewHolder: last donate "+user.last_donate )

        if (user.last_donate == "0" && user.last_donate.equals(0))
        {
            holder.binding.tvLastDonate.visibility = View.INVISIBLE
        }
        else
        {
            holder.binding.tvLastDonate.visibility = View.VISIBLE
            holder.binding.tvLastDonate.text = user.last_donate
        }
        Log.e("TAG123456789", "onBindViewHolder: last donate " + user.last_donate);

// Check if user.last_donate is equal to "0" or 0
        if ("0".equals(user.last_donate) || Integer.parseInt(user.last_donate) == 0) {
            holder.binding.tvLastDonate.visibility = View.INVISIBLE;
        } else {
            holder.binding.tvLastDonate.visibility = View.VISIBLE;
            holder.binding.tvLastDonate.text = context.getString(R.string.last_donate_)+user.last_donate;
        }


        holder.binding.ivCall.setOnClickListener {
            clickToCall.onClickCall(user)
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
}