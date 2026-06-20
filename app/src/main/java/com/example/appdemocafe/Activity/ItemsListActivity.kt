package com.example.appdemocafe.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdemocafe.adapter.ItemsListCategoryAdapter
import com.example.appdemocafe.viewmodel.MainViewModel
import com.example.appdemocafe.databinding.ActivityItemsListBinding

class ItemsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemsListBinding
    private val viewModel = MainViewModel()
    private var categoryId: String = ""
    private var categoryName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItemsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        initList()
    }

    private fun initList() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            viewModel.loadItemCategory(categoryId).observe(this@ItemsListActivity) {
                listView.layoutManager = GridLayoutManager(this@ItemsListActivity, 2)
                listView.adapter = ItemsListCategoryAdapter(it)
                progressBar.visibility = View.GONE
            }

            backBtn.setOnClickListener { finish() }
            categoryTxt.text = categoryName
        }
    }

    private fun getBundle() {
        categoryId = intent.getIntExtra("id", 0).toString()
        categoryName = intent.getStringExtra("title") ?: ""
    }
}
