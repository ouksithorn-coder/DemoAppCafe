package com.example.appdemocafe.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdemocafe.R
import com.example.appdemocafe.adapter.SettingAdapter
import com.example.appdemocafe.databinding.ActivityProfileBinding
import com.example.appdemocafe.domain.SettingModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomMenu()
        initEditProfile()
        initSettingList()
    }

    private fun initSettingList() {
        val settings = listOf(
            SettingModel("Set PIN Payment", R.drawable.settings),
            SettingModel("Saved Address", R.drawable.settings),
            SettingModel("Privacy Setting", R.drawable.settings),
            SettingModel("Language", R.drawable.settings),
            SettingModel("Billing Payment", R.drawable.settings)
        )

        binding.settingRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.settingRecyclerView.adapter = SettingAdapter(settings)
    }

    private fun initEditProfile() {
        binding.editBtn.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
        binding.couponCard.setOnClickListener {
            // startActivity(Intent(this, ClaimVoucherActivity::class.java))
        }
    }

    private fun initBottomMenu() {
        binding.explorerBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.wishlistBtn.setOnClickListener {
            startActivity(Intent(this, WishlistActivity::class.java))
        }
        binding.orderBtn.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
        }
    }
}