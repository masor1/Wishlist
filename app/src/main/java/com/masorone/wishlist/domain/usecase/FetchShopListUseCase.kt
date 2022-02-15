package com.masorone.wishlist.domain.usecase

import com.masorone.wishlist.domain.repository.ShopItemRepository

class FetchShopListUseCase(private val shopItemRepository: ShopItemRepository) {

    fun fetch() = shopItemRepository.fetch()
}