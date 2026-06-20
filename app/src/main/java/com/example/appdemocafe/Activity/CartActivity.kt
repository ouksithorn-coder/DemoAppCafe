package com.example.appdemocafe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemocafe.adapter.CartAdapter
import com.example.appdemocafe.domain.OrderModel
import com.example.appdemocafe.helper.ChangeNumberItemsListener
import com.example.appdemocafe.helper.ManagementCart
import com.example.appdemocafe.helper.ManagementOrder
import com.example.appdemocafe.R
import com.example.appdemocafe.databinding.ActivityCartBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagementCart
    private lateinit var managementOrder: ManagementOrder
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)
        managementOrder = ManagementOrder(this)

        calculateCart()
        setVariable()
        initCartList()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener { }
        binding.wishlistBtn.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }
        binding.orderBtn.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }
        binding.profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.explorerBtn.setOnClickListener {
            finish()
        }
    }

    private fun initCartList() {
        binding.listView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.listView.adapter = CartAdapter(
            managementCart.getListCart(),
            this,
            object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()
                }
            },
        )
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
        binding.button.setOnClickListener {
            val listCart = managementCart.getListCart()
            if (listCart.isNotEmpty()) {
                val calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
                val date = dateFormat.format(calendar.time)

                val order = OrderModel(
                    items = ArrayList(listCart),
                    totalPrice = ((managementCart.getTotalFee() + tax + 15) * 100) / 100.0,
                    date = date,
                )
                managementOrder.saveOrder(order)
                managementOrder.clearCart()
                Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, OrderHistoryActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calculateCart() {
        val percentTxt = 0.02
        val delivery = 15.0
        val subtotal = (managementCart.getTotalFee() * 100) / 100.0
        tax = ((subtotal * percentTxt) * 100) / 100.0
        val total = ((subtotal + tax + delivery) * 100) / 100.0
        
        binding.totalFeeTxt.text = getString(R.string.currency_placeholder, subtotal.toString())
        binding.totalTaxTxt.text = getString(R.string.currency_placeholder, tax.toString())
        binding.deliveryTxt.text = getString(R.string.currency_placeholder, delivery.toString())
        binding.totalTxt.text = getString(R.string.currency_placeholder, total.toString())
    }
}
