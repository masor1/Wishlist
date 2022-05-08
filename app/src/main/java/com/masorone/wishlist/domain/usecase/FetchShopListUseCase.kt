package com.masorone.wishlist.domain.usecase

import com.masorone.wishlist.domain.repository.ShopItemRepository
import javax.inject.Inject

class FetchShopListUseCase @Inject constructor(
    private val shopItemRepository: ShopItemRepository
) {

    fun fetch() = shopItemRepository.fetch()
}