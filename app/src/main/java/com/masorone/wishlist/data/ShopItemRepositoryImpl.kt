package com.masorone.wishlist.data

import androidx.lifecycle.MutableLiveData
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.repository.ShopItemRepository

object ShopItemRepositoryImpl : ShopItemRepository {

    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = sortedSetOf(Comparator<ShopItem> { shopItem1, shopItem2 ->
        shopItem1.id.compareTo(shopItem2.id)
    })

    private var autoIncrementId = 0

    override fun add(shopItem: ShopItem) {
        if (shopItem.undefinedId())
            shopItem.id = autoIncrementId++
        shopList.add(shopItem)
        updateList()
    }

    override fun delete(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun edit(shopItem: ShopItem) {
        val oldElement = fetch(shopItem.id)
        shopList.remove(oldElement)
        add(shopItem)
    }

    override fun fetch(): MutableLiveData<List<ShopItem>> {
        updateList()
        return shopListLD
    }

    override fun fetch(shopItemId: Int) = shopList.find { it.id == shopItemId }
        ?: throw RuntimeException("Element with id $shopItemId not found")

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}