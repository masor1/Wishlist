package com.masorone.wishlist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.masorone.wishlist.R
import com.masorone.wishlist.databinding.ActivityMainBinding
import com.masorone.wishlist.presentation.adapter.ShopListAdapter
import com.masorone.wishlist.presentation.fragment.ShopItemFragment
import com.masorone.wishlist.presentation.wrapper.TouchHelperWrapper

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupRecyclerView()
        setupAddButton()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { listOfShopItem ->
            shopListAdapter.submitList(listOfShopItem)
        }
    }

    private fun onFragmentFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun setupRecyclerView() {
        setupAdapterAndViewPool()
        setupSwipeItemListener()
        setupClickItemListener()
        setupLongClickItemListener()
    }

    private fun setupAdapterAndViewPool() {
        shopListAdapter = ShopListAdapter()
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

    private fun isOnePaneMode() = binding.shopItemContainer == null

    private fun launchFragment(fragment: ShopItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupLongClickItemListener() {
        shopListAdapter.onShopItemLongClickListeners = { shopItem ->
            viewModel.changeStateShopItem(shopItem)
        }
    }

    private fun setupClickItemListener() {
        shopListAdapter.onShopItemClickListener = { shopItem ->
            val shopItemId = shopItem.id
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditMode(this, shopItemId)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceEditMode(shopItemId)
                launchFragment(fragment)
            }
        }
    }

    private fun setupSwipeItemListener() {
        val itemTouchHelper = ItemTouchHelper(
            object : TouchHelperWrapper() {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.deleteShopItem(shopListAdapter.currentList[viewHolder.adapterPosition])
                }
            }
        )
        itemTouchHelper.attachToRecyclerView(binding.rvShopList)
    }

    private fun setupAddButton() {
        binding.buttonAddShopItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddMode(this)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.newInstanceAddMode()
                launchFragment(fragment)
            }
        }
    }

    override fun onFinished() {
        onFragmentFinished()
    }
}