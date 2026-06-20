package com.example.appdemocafe.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemocafe.adapter.OrderDetailAdapter
import com.example.appdemocafe.domain.OrderModel
import com.example.appdemocafe.R
import com.example.appdemocafe.databinding.ActivityOrderItemsBinding

class OrderItemsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderItemsBinding
    private var order: OrderModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrderItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        initList()
    }

    private fun getBundle() {
        order = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("object", OrderModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("object") as? OrderModel
        }
        
        order?.let {
            binding.totalPriceTxt.text = getString(R.string.currency_placeholder, it.totalPrice.toString())
        }
        
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun initList() {
        order?.let {
            binding.itemsView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.itemsView.adapter = OrderDetailAdapter(it.items)
        }
    }
}
