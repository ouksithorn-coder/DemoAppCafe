package com.example.appdemocafe.helper

import android.content.Context
import android.widget.Toast
import com.example.appdemocafe.domain.ItemsModel

class ManagementWishlist(val context: Context) {
    private val tinyDB = TinyDB(context)

    fun getWishlist(): ArrayList<ItemsModel> {
        return tinyDB.getListObject("Wishlist", ItemsModel::class.java) ?: arrayListOf()
    }

    fun toggleWishlist(item: ItemsModel) {
        val list = getWishlist()
        val index = list.indexOfFirst { it.title == item.title }
        if (index != -1) {
            list.removeAt(index)
            Toast.makeText(context, "Removed from Wishlist", Toast.LENGTH_SHORT).show()
        } else {
            list.add(item)
            Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show()
        }
        tinyDB.putListObject("Wishlist", list)
    }

    fun isFavorite(item: ItemsModel): Boolean {
        return getWishlist().any { it.title == item.title }
    }
}
