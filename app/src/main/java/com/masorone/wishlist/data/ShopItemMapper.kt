package com.masorone.wishlist.data

import com.masorone.wishlist.domain.model.ShopItem
import javax.inject.Inject

class ShopItemMapper @Inject constructor() {

    fun mapToDb(shopItem: ShopItem) = ShopItemDb(
        shopItem.id,
        shopItem.name,
        shopItem.count,
        shopItem.enabled
    )

    fun mapToDomain(shopItem: ShopItemDb) = ShopItem(
        shopItem.name,
        shopItem.count,
        shopItem.enabled,
        shopItem.id
    )

    fun mapToListOfDomain(list: List<ShopItemDb>) = list.map { shopItemDb ->
        mapToDomain(shopItemDb)
    }
}