package com.example.uos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mycartooncharacter.model.IntroModel
import com.example.uos.databinding.RowIntroScreenBinding

class IntroPagerAdapter(
    var arrIntro: ArrayList<IntroModel>,
    var context: Context
) : RecyclerView.Adapter<IntroPagerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowIntroScreenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = RowIntroScreenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val introModel = arrIntro[position]


//        HelperResizer.setHeight(context,holder.binding.ivImage,1080)
        Glide.with(context).load(introModel.guideImage).into(holder.binding.ivImage)

        holder.binding.ivTitle.text = "" + introModel.title
        holder.binding.ivBody.text = "" + introModel.desc
    }

    override fun getItemCount(): Int {
        return if (arrIntro.isEmpty()) {
            0;
        } else {
            arrIntro.size
        }
    }
}

