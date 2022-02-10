package com.masorone.wishlist.domain.repository

import com.masorone.wishlist.domain.model.ShopItem

interface ShopItemRepository {

    fun add(shopItem: ShopItem)

    fun delete(shopItem: ShopItem)

    fun edit(shopItem: ShopItem)

    fun fetch(): List<ShopItem>

    fun fetch(shopItemId: Int): ShopItem
}