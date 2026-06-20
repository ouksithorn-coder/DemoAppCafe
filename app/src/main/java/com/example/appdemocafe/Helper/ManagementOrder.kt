package com.example.appdemocafe.helper

import android.content.Context
import com.example.appdemocafe.domain.OrderModel

class ManagementOrder(context: Context) {
    private val tinyDB = TinyDB(context)

    fun saveOrder(order: OrderModel) {
        val orders = getOrders()
        orders.add(order)
        tinyDB.putListObject("OrderList", orders)
    }

    fun getOrders(): ArrayList<OrderModel> {
        val orders = tinyDB.getListObject("OrderList", OrderModel::class.java)
        return orders ?: arrayListOf()
    }

    fun clearCart() {
        tinyDB.remove("CartList")
    }
}
