package com.masorone.wishlist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.masorone.wishlist.databinding.ActivityShopItemBinding

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            return intent
        }
    }
}