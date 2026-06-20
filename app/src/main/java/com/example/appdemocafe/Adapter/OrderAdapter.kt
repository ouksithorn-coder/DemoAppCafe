package com.example.appdemocafe.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdemocafe.activity.OrderItemsActivity
import com.example.appdemocafe.domain.OrderModel
import com.example.appdemocafe.R
import com.example.appdemocafe.databinding.ViewholderOrderBinding

class OrderAdapter(private val orders: ArrayList<OrderModel>) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: ViewholderOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderOrderBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.binding.orderDateTxt.text = order.date
        holder.binding.statusTxt.text = order.status
        holder.binding.totalPriceTxt.text = context.getString(R.string.currency_placeholder, order.totalPrice.toString())
        holder.binding.itemsSummaryTxt.text = "${order.items.size} items"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, OrderItemsActivity::class.java)
            intent.putExtra("object", order)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = orders.size
}
