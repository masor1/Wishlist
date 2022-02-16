package com.masorone.wishlist.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.masorone.wishlist.R
import com.masorone.wishlist.databinding.ActivityShopItemBinding
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.presentation.fragment.ShopItemFragment

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var binding: ActivityShopItemBinding

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINE_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        parseIntent()
        if (savedInstanceState == null)
            startMode(screenMode)
    }

    private fun startMode(screenMode: String) {
        val fragment = when (screenMode) {
            MODE_ADD -> ShopItemFragment.newInstanceAddMode()
            MODE_EDIT -> ShopItemFragment.newInstanceEditMode(shopItemId)
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(MODE_KEY)) throw RuntimeException("Param screen mode is absent")
        val mode = intent.getStringExtra(MODE_KEY)
        if (mode != MODE_EDIT && mode != MODE_ADD) throw RuntimeException("Unknown screen mode $mode")
        screenMode = mode
        if (screenMode == MODE_EDIT)
            if (!intent.hasExtra(SHOP_ITEM_ID)) throw RuntimeException("Param shop item id is absent")
        shopItemId = intent.getIntExtra(SHOP_ITEM_ID, ShopItem.UNDEFINE_ID)
    }

    companion object {

        private const val SHOP_ITEM_ID = "shop_item_id"

        private const val MODE_KEY = "mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddMode(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(MODE_KEY, MODE_ADD)
            return intent
        }

        fun newIntentEditMode(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(SHOP_ITEM_ID, shopItemId)
            intent.putExtra(MODE_KEY, MODE_EDIT)
            return intent
        }
    }

    private fun onFragmentFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onFinished() {
        onFragmentFinished()
    }
}