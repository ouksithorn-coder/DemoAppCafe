package com.example.appdemocafe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdemocafe.databinding.ViewholderSettingBinding
import com.example.appdemocafe.domain.SettingModel

class SettingAdapter(private val items: List<SettingModel>) :
    RecyclerView.Adapter<SettingAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderSettingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderSettingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.settingTitle.text = item.title
        holder.binding.settingIcon.setImageResource(item.iconRes)
    }

    override fun getItemCount(): Int = items.size
}