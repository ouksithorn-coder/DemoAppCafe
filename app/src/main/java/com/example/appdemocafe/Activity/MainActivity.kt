package com.example.appdemocafe.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.appdemocafe.adapter.CategoryAdapter
import com.example.appdemocafe.adapter.PopularAdapter
import com.example.appdemocafe.domain.CategoryModel
import com.example.appdemocafe.viewmodel.MainViewModel
import com.example.appdemocafe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initBanner()
        initCategory()
        initPopular()
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
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.explorerBtn.setOnClickListener {
            binding.nestedScrollView.smoothScrollTo(0, 0)
        }
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.loadPopular().observe(this) {
            val adapter = PopularAdapter(it)
            binding.popularView.layoutManager = GridLayoutManager(this, 2)
            binding.popularView.adapter = adapter
            binding.progressBarPopular.visibility = View.GONE

            // Show "See all" if there are more than 8 items
            if (it.size > 8) {
                binding.textView5.visibility = View.VISIBLE
                binding.textView5.setOnClickListener {
                    adapter.setExpanded(expanded = true)
                    binding.textView5.visibility = View.GONE
                    
                    // Force the ScrollView to update its height to accommodate new items
                    binding.nestedScrollView.post {
                        binding.popularView.requestLayout()
                    }
                }
            } else {
                binding.textView5.visibility = View.GONE
            }
        }
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.loadCategory().observe(this) {
            val list = it.toMutableList()
            
            // Adding "Iced" as the first item if it's not already there
            if (list.none { cat -> cat.title.lowercase() == "iced" }) {
                list.add(0, CategoryModel("Iced", 99, "https://firebasestorage.googleapis.com/v0/b/appdemocafe.appspot.com/o/iced_coffee_icon.png?alt=media"))
            }

            binding.categoryView.layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false,
            )
            binding.categoryView.adapter = CategoryAdapter(list)
            binding.progressBarCategory.visibility = View.GONE
        }
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.loadBanner().observe(this) {
            Glide.with(this@MainActivity)
                .load(it[0].url)
                .into(binding.banner)
            binding.progressBarBanner.visibility = View.GONE
        }
    }
}
