package com.masorone.wishlist.domain.usecase

import com.masorone.wishlist.domain.repository.ShopItemRepository

class FetchShopItemUseCase(private val shopItemRepository: ShopItemRepository) {

    suspend fun fetch(shopItemId: Int) = shopItemRepository.fetch(shopItemId)
}