package com.masorone.wishlist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.masorone.wishlist.R
import com.masorone.wishlist.databinding.ItemShopDisabledBinding
import com.masorone.wishlist.databinding.ItemShopEnabledBinding
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.presentation.adapter.diff_util.ShopItemDiffCallback

class ShopListAdapter : ListAdapter<ShopItem, ShopListAdapter.ShopItemViewHolder>(
    ShopItemDiffCallback()
) {
    var onShopItemLongClickListeners: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            0 -> R.layout.item_shop_disabled
            else -> R.layout.item_shop_enabled
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        holder.bind(shopItem)
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    inner class ShopItemViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shopItem: ShopItem) {
            when (binding) {
                is ItemShopEnabledBinding -> {
                    binding.cvName.text = shopItem.name
                    binding.cvCount.text = shopItem.count.toString()
                }
                is ItemShopDisabledBinding -> {
                    binding.cvName.text = shopItem.name
                    binding.cvCount.text = shopItem.count.toString()
                }
            }

            with(binding.root) {
                setOnLongClickListener {
                    onShopItemLongClickListeners?.invoke(shopItem)
                    true
                }
                setOnClickListener {
                    onShopItemClickListener?.invoke(shopItem)
                }
            }
        }
    }

    companion object {

        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0

        const val MAX_POOL_SIZE = 10
    }
}