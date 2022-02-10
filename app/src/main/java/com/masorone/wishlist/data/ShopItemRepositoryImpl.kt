package com.masorone.wishlist.data

import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.repository.ShopItemRepository
import java.lang.RuntimeException

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun add(shopItem: ShopItem) {
        if (shopItem.undefinedId()) {
            shopItem.id = autoIncrementId++
            shopList.add(shopItem)
        }
        shopList.add(shopItem)
    }

    override fun delete(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun edit(shopItem: ShopItem) {
        val oldElement = fetch(shopItem.id)
        shopList.remove(oldElement)
        add(shopItem)
    }

    override fun fetch() = shopList.toList()

    override fun fetch(shopItemId: Int) = shopList.find { it.id == shopItemId }
        ?: throw RuntimeException("Element with id $shopItemId not found")
}