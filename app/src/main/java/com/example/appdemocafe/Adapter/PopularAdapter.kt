package com.example.appdemocafe.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdemocafe.activity.DetailActivity
import com.example.appdemocafe.domain.ItemsModel
import com.example.appdemocafe.helper.ManagementCart
import com.example.appdemocafe.helper.ManagementWishlist
import com.example.appdemocafe.R
import com.example.appdemocafe.databinding.ViewholderPopularBinding

class PopularAdapter(val items: MutableList<ItemsModel>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var isExpanded = false
    private lateinit var managementWishlist: ManagementWishlist
    private lateinit var managementCart: ManagementCart

    class ViewHolder(val binding: ViewholderPopularBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        context = parent.context
        managementWishlist = ManagementWishlist(context)
        managementCart = ManagementCart(context)
        val binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false)
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

        if (managementWishlist.isFavorite(item)) {
            holder.binding.favBtn.setColorFilter(ContextCompat.getColor(context, R.color.orange))
        } else {
            holder.binding.favBtn.clearColorFilter()
        }

        holder.binding.favBtn.setOnClickListener {
            managementWishlist.toggleWishlist(item)
            notifyItemChanged(position)
        }

        holder.binding.plusBtn.setOnClickListener {
            item.numberInCart = 1
            managementCart.insertItems(item)
        }
    }

    fun setExpanded(expanded: Boolean) {
        this.isExpanded = expanded
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (isExpanded || (items.size <= 8)) {
            items.size
        } else {
            8
        }
    }
}
