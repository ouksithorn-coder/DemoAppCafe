package com.example.appdemocafe.domain

import java.io.Serializable

data class OrderModel(
    var items: ArrayList<ItemsModel> = ArrayList(),
    var totalPrice: Double = 0.0,
    var date: String = "",
    var status: String = "Pending"
) : Serializable
