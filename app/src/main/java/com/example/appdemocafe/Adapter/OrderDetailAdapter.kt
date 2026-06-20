package com.example.appdemocafe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdemocafe.domain.ItemsModel
import com.example.appdemocafe.R
import com.example.appdemocafe.databinding.ViewholderOrderItemBinding

class OrderDetailAdapter(private val items: ArrayList<ItemsModel>) :
    RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>() {

    class ViewHolder(val binding: ViewholderOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        
        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = context.getString(R.string.currency_placeholder, item.price.toString())
        holder.binding.qtyTxt.text = "x${item.numberInCart}"

        if (item.picUrl.isNotEmpty()) {
            Glide.with(context)
                .load(item.picUrl[0])
                .into(holder.binding.picItem)
        }
    }

    override fun getItemCount(): Int = items.size
}
