package com.masorone.wishlist.domain.usecase

import com.masorone.wishlist.domain.repository.ShopItemRepository
import javax.inject.Inject

class FetchShopItemUseCase @Inject constructor(
    private val shopItemRepository: ShopItemRepository
) {

    suspend fun fetch(shopItemId: Int) = shopItemRepository.fetch(shopItemId)
}