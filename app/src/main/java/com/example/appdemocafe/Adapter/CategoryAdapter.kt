package com.example.appdemocafe.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdemocafe.R
import com.example.appdemocafe.activity.ItemsListActivity
import com.example.appdemocafe.domain.CategoryModel
import com.example.appdemocafe.databinding.ViewholderCategoryBinding

class CategoryAdapter(val items: MutableList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleCat.text = item.title
        val imageResource = when (position) {
            0 -> R.drawable.ice
            1 -> R.drawable.expresso
            2 -> R.drawable.cappuccino
            3 -> R.drawable.latte
            4 -> R.drawable.black_cafe
            else -> R.drawable.choco
        }

        holder.binding.picCat.setImageResource(imageResource)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ItemsListActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("title", item.title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
