package com.masorone.wishlist.domain.repository

import androidx.lifecycle.MutableLiveData
import com.masorone.wishlist.domain.model.ShopItem

interface ShopItemRepository {

    fun add(shopItem: ShopItem)

    fun delete(shopItem: ShopItem)

    fun edit(shopItem: ShopItem)

    fun fetch(): MutableLiveData<List<ShopItem>>

    fun fetch(shopItemId: Int): ShopItem
}