package com.masorone.wishlist.domain.usecase

import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.repository.ShopItemRepository

class EditShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    fun edit(shopItem: ShopItem) = shopItemRepository.edit(shopItem)
}