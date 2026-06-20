package com.example.appdemocafe.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appdemocafe.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.wishlistBtn.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }
        binding.orderBtn.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }
        binding.profileBtn.setOnClickListener {

        }
        binding.explorerBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
