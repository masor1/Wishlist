package com.masorone.wishlist.presentation.adapters.utils

import androidx.recyclerview.widget.DiffUtil
import com.masorone.wishlist.domain.model.ShopItem

class ShopItemDiffCallback : DiffUtil.ItemCallback<ShopItem>() {

    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem == newItem
}