package com.krunal.uos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycartooncharacter.model.LanguageModel
import com.example.mycartooncharacter.utils.HelperResizer
import com.krunal.uos.databinding.RowLanguageBinding
import com.krunal.uos.interfaces.OnCheckLanguage

class LanguageAdapter(
    var arrLanguage: ArrayList<LanguageModel>,
    val context: Context,
    var onCheckLanguage: OnCheckLanguage
) : RecyclerView.Adapter<LanguageAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowLanguageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (arrLanguage.isEmpty()) {
            0
        } else {
            arrLanguage.size
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val languageModel = arrLanguage[position]

        HelperResizer.getHeightAndWidth(context)
        HelperResizer.setSize(holder.binding.rlBg, 1020, 150, true)
        HelperResizer.setSize(holder.binding.rbCheck, 66, 66, true)
        HelperResizer.setSize(holder.binding.ivCountry, 156, 85, true)

        holder.binding.ivCountry.setImageResource(languageModel.countryImage)
        holder.binding.tvCountryName.text = languageModel.LanguageName
        holder.binding.rbCheck.isChecked = languageModel.isChecked

        if (arrLanguage[holder.adapterPosition].isChecked) {
            holder.binding.rbCheck.isSelected = true
            holder.binding.rlBg.isSelected = true
        } else {
            holder.binding.rbCheck.isSelected = false
            holder.binding.rlBg.isSelected = false
        }

        holder.itemView.setOnClickListener {
            unSelectAll()
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                arrLanguage[holder.adapterPosition].isChecked = true
                onCheckLanguage.onClickItem(arrLanguage[holder.adapterPosition])
            }
        }


    }

    private fun unSelectAll() {
        arrLanguage.forEach { it.isChecked = false }
    }

}