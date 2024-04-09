package com.krunal.uos.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.krunal.uos.R
import com.krunal.uos.databinding.RowEventBinding
import com.krunal.uos.databinding.RowGalleryBinding
import com.krunal.uos.interfaces.OnClickImage
import com.krunal.uos.model.Event
import com.krunal.uos.utils.Config

class EventAdapter(
    private var context: Context,
    private var arrEvent: ArrayList<Event>,
    private var onClickImage: OnClickImage
) : RecyclerView.Adapter<com.krunal.uos.adapter.EventAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowEventBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = arrEvent[position]
        Log.e("TAG123456789", "onBindViewHolder:  gallery "+event )
        val url = event.image.toString().trim()
        Log.e("TAG123456789", "onBindViewHolder:  gallery link "+Config.BASE_URL+"event/"+url )
        // Load image using Picasso/Glicde or any other library
         Glide.with(context).load(Config.BASE_URL+"event/"+url)
             .apply(RequestOptions().override(640, 640))
             .placeholder(R.drawable.uos)
             .into(holder.binding.ivImage)

        holder.binding.tvName.text = ""+event.name
        holder.binding.tvDate.text = ""+event.date

        holder.binding.ivImage.setOnClickListener {
            onClickImage.clickOnImage(url,event.date)
        }

    }

    override fun getItemCount(): Int {
       return arrEvent.size
    }

}
