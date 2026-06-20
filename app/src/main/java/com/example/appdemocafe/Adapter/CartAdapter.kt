package com.example.appdemocafe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdemocafe.domain.ItemsModel
import com.example.appdemocafe.helper.ChangeNumberItemsListener
import com.example.appdemocafe.helper.ManagementCart
import com.example.appdemocafe.R
import com.example.appdemocafe.databinding.ViewholderCartBinding

class CartAdapter(
    private val listItemSelected: ArrayList<ItemsModel>,
    private val context: Context,
    private val changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val managementCart = ManagementCart(context)

    class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItemSelected[position]

        holder.binding.titleTxt.text = item.title
        holder.binding.feeEachItem.text = context.getString(R.string.currency_placeholder, item.price.toString())
        holder.binding.totalEachItem.text = context.getString(R.string.currency_placeholder, (item.numberInCart * item.price).toString())
        holder.binding.numberInCartTxt.text = item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .into(holder.binding.picCart)

        holder.binding.plusBtn.setOnClickListener {
            managementCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }
            })
        }

        holder.binding.minusBtn.setOnClickListener {
            managementCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }
            })
        }

        holder.binding.removeItemBtn.setOnClickListener {
            managementCart.removeItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int = listItemSelected.size
}
