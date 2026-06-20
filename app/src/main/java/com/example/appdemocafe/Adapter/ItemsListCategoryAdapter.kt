package com.example.appdemocafe.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdemocafe.activity.DetailActivity
import com.example.appdemocafe.domain.ItemsModel
import com.example.appdemocafe.R
import com.example.appdemocafe.databinding.ViewholderItemListBinding

class ItemsListCategoryAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<ItemsListCategoryAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: ViewholderItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderItemListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = context.getString(R.string.currency_placeholder, item.price.toString())
        holder.binding.subtitleTxt.text = item.extra

        Glide.with(context)
            .load(item.picUrl[0])
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("object", item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
