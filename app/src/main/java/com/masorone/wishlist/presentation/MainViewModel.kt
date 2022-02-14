package com.masorone.wishlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.masorone.wishlist.data.ShopItemRepositoryImpl
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.usecase.AddShopItemUseCase
import com.masorone.wishlist.domain.usecase.DeleteShopItemUseCase
import com.masorone.wishlist.domain.usecase.EditShopItemUseCase
import com.masorone.wishlist.domain.usecase.FetchShopListUseCase

class MainViewModel : ViewModel() {

    private val shopItemRepository = ShopItemRepositoryImpl

    private val fetchShopListUseCase = FetchShopListUseCase(shopItemRepository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopItemRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopItemRepository)

    private val addShopItemUseCase = AddShopItemUseCase(shopItemRepository)

    private val _shopList = fetchShopListUseCase.fetch()
    val shopList: LiveData<List<ShopItem>>
        get() = _shopList

    fun addShopItem(shopItem: ShopItem) {
        shopItemRepository.add(shopItem)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.delete(shopItem)
    }

    fun changeStateShopItem(shopItem: ShopItem) {
        editShopItemUseCase.edit(shopItem.copy(enabled = !shopItem.enabled))
    }
}