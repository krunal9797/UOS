package com.example.uos.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.uos.databinding.RowGalleryBinding
import com.example.uos.interfaces.OnClickImage
import com.example.uos.model.Gallery
import com.example.uos.utils.Config

class GalleryAdapter(
    private val context: Context,
    private var arrGallery: ArrayList<Gallery>,
    var value: Int,
    var onClickImage: OnClickImage
) : RecyclerView.Adapter<GalleryAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowGalleryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowGalleryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val gallery = arrGallery[position]
        Log.e("TAG123456789", "onBindViewHolder:  gallery "+gallery )
        val url = gallery.image.toString().trim()
        Log.e("TAG123456789", "onBindViewHolder:  gallery link "+Config.BASE_URL+"gallery/"+url )
        // Load image using Picasso/Glicde or any other library
         Glide.with(context).load(Config.BASE_URL+"gallery/"+url)
             .apply(RequestOptions().override(720, 720))
             .into(holder.binding.ivImage)

        holder.binding.ivImage.setOnClickListener {
            onClickImage.clickOnImage(url,gallery.date)
        }

    }

    override fun getItemCount(): Int {
        return if (value == 0) {
            if (arrGallery.size >= 3) {
                3
            } else {
                arrGallery.size
            }
        } else {
            arrGallery.size
        }
    }

}
