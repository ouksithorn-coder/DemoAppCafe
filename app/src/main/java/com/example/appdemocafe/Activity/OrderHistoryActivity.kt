package com.example.appdemocafe.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemocafe.adapter.OrderAdapter
import com.example.appdemocafe.helper.ManagementOrder
import com.example.appdemocafe.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var managementOrder: ManagementOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementOrder = ManagementOrder(this)

        initList()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
            finish()
        }
        binding.wishlistBtn.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
            finish()
        }
        binding.orderBtn.setOnClickListener {

        }
        binding.profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
        binding.explorerBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initList() {
        val orders = managementOrder.getOrders()
        
        if (orders.isEmpty()) {
            binding.emptyLayout.visibility = View.VISIBLE
            binding.orderView.visibility = View.GONE
        } else {
            binding.emptyLayout.visibility = View.GONE
            binding.orderView.visibility = View.VISIBLE
            binding.orderView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.orderView.adapter = OrderAdapter(orders)
        }

        binding.backBtn.setOnClickListener { finish() }
    }
}
