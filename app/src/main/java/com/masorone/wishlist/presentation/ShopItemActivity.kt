package com.masorone.wishlist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.masorone.wishlist.R
import com.masorone.wishlist.databinding.ActivityShopItemBinding
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.presentation.wrapper.TextWatcherWrapper

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding

    private lateinit var viewModel: ShopItemViewModel
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINE_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        parseIntent()
        initViewModel()
        startMode(screenMode)
        setupTextChangeListeners()
        endWork()
    }

    private fun startMode(screenMode: String) {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchEditMode() {
        viewModel.fetch(shopItemId)

        viewModel.shopItem.observe(this) { shopItem ->
            binding.etName.setText(shopItem.name)
            binding.etCount.setText(shopItem.count.toString())
        }
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val count = binding.etCount.text.toString()
            viewModel.edit(name, count)
        }
    }

    private fun launchAddMode() {
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val count = binding.etCount.text.toString()
            viewModel.add(name, count)
        }
    }

    private fun endWork() {
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun setupTextChangeListeners() {
        viewModel.errorInputName.observe(this) {
            binding.tilName.error = if (it) getString(R.string.error_input_name) else null
        }
        viewModel.errorInputCount.observe(this) {
            binding.tilCount.error = if (it) getString(R.string.error_input_count) else null
        }

        val textWatcherName = object : TextWatcherWrapper() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }
        }

        val textWatcherCount = object : TextWatcherWrapper() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }
        }

        binding.etName.addTextChangedListener(textWatcherName)
        binding.etCount.addTextChangedListener(textWatcherCount)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
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
}