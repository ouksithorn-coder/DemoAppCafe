package com.example.appdemocafe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.appdemocafe.domain.BannerModel
import com.example.appdemocafe.domain.CategoryModel
import com.example.appdemocafe.domain.ItemsModel
import com.example.appdemocafe.repository.MainRepository

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return repository.loadBanner()
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadPopular(): LiveData<MutableList<ItemsModel>> {
        return repository.loadPopular()
    }

    fun loadItemCategory(categoryId: String): LiveData<MutableList<ItemsModel>> {
        return repository.loadItemCategory(categoryId)
    }
}
