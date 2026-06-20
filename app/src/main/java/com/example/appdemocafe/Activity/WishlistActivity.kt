package com.example.appdemocafe.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdemocafe.adapter.PopularAdapter
import com.example.appdemocafe.helper.ManagementWishlist
import com.example.appdemocafe.databinding.ActivityWishlistBinding

class WishlistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishlistBinding
    private lateinit var managementWishlist: ManagementWishlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWishlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementWishlist = ManagementWishlist(this)

        initList()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.wishlistBtn.setOnClickListener {

        }
        binding.orderBtn.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }
        binding.profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.explorerBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initList() {
        val list = managementWishlist.getWishlist()
        if (list.isEmpty()) {
            binding.emptyLayout.visibility = View.VISIBLE
            binding.wishlistView.visibility = View.GONE
        } else {
            binding.emptyLayout.visibility = View.GONE
            binding.wishlistView.visibility = View.VISIBLE
            binding.wishlistView.layoutManager = GridLayoutManager(this, 2)
            binding.wishlistView.adapter = PopularAdapter(list)
        }

        binding.backBtn.setOnClickListener { finish() }
    }
}
