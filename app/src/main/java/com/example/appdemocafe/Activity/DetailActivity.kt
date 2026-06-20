package com.example.appdemocafe.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.appdemocafe.domain.ItemsModel
import com.example.appdemocafe.helper.ManagementCart
import com.example.appdemocafe.helper.ManagementWishlist
import com.example.appdemocafe.R
import com.example.appdemocafe.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder = 1
    private lateinit var managementCart: ManagementCart
    private lateinit var managementWishlist: ManagementWishlist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)
        managementWishlist = ManagementWishlist(this)

        getBundle()
    }

    private fun getBundle() {
        item = intent.getSerializableExtra("object") as ItemsModel

        Glide.with(this)
            .load(item.picUrl[0])
            .into(binding.picMain)

        binding.titleTxt.text = item.title
        binding.priceTxt.text = getString(R.string.currency_placeholder, item.price.toString())
        binding.descriptionTxt.text = item.description
        binding.ratingTxt.text = item.rating.toString()

        binding.addToCartBtn.setOnClickListener {
            item.numberInCart = numberOrder
            managementCart.insertItems(item)
        }

        binding.backBtn.setOnClickListener { finish() }

        binding.plusBtn.setOnClickListener {
            numberOrder++
            binding.numberInCartTxt.text = numberOrder.toString()
        }

        binding.minusBtn.setOnClickListener {
            if (numberOrder > 1) {
                numberOrder--
                binding.numberInCartTxt.text = numberOrder.toString()
            }
        }

        if (managementWishlist.isFavorite(item)) {
            binding.favBtn.setColorFilter(ContextCompat.getColor(this, R.color.orange))
        } else {
            binding.favBtn.clearColorFilter()
        }

        binding.favBtn.setOnClickListener {
            managementWishlist.toggleWishlist(item)
            if (managementWishlist.isFavorite(item)) {
                binding.favBtn.setColorFilter(ContextCompat.getColor(this, R.color.orange))
            } else {
                binding.favBtn.clearColorFilter()
            }
        }
    }
}
