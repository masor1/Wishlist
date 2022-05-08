package com.masorone.wishlist.domain.usecase

import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.repository.ShopItemRepository
import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor(
    private val shopItemRepository: ShopItemRepository
) {

    suspend fun delete(shopItem: ShopItem) = shopItemRepository.delete(shopItem)
}