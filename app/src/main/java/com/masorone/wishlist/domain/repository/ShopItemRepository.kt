package com.masorone.wishlist.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.masorone.wishlist.domain.model.ShopItem

interface ShopItemRepository {

    suspend fun add(shopItem: ShopItem)

    suspend fun delete(shopItem: ShopItem)

    suspend fun edit(shopItem: ShopItem)

    fun fetch(): LiveData<List<ShopItem>>

    suspend fun fetch(shopItemId: Int): ShopItem
}