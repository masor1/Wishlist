package com.masorone.wishlist.domain.usecase

import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.repository.ShopItemRepository

class AddShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    fun add(shopItem: ShopItem) = shopItemRepository.add(shopItem)
}