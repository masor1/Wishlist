package com.masorone.wishlist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.masorone.wishlist.databinding.ActivityMainBinding
import com.masorone.wishlist.presentation.adapters.ShopListAdapter
import com.masorone.wishlist.presentation.adapters.utils.TouchHelperSimpleCallback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { listOfShopItem ->
            shopListAdapter.submitList(listOfShopItem)
        }
    }

    private fun setupRecyclerView() {
        shopListAdapter = ShopListAdapter()
        setupAdapterAndViewPool()
        setupSwipeListener()
        setupClickListener()
        setupLongClickListener()
    }

    private fun setupAdapterAndViewPool() {
        with(binding.rvShopList) {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListeners = { shopItem ->
            viewModel.changeStateShopItem(shopItem)
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = { shopItem ->
            val intent = ShopItemActivity.newIntent(this)
            startActivity(intent)
        }
    }

    private fun setupSwipeListener() {
        val itemTouchHelper = ItemTouchHelper(
            object : TouchHelperSimpleCallback() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.deleteShopItem(shopListAdapter.currentList[viewHolder.adapterPosition])
                }
            }
        )
        itemTouchHelper.attachToRecyclerView(binding.rvShopList)
    }
}