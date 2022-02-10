package com.masorone.wishlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.masorone.wishlist.data.ShopItemRepositoryImpl
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.usecase.DeleteShopItemUseCase
import com.masorone.wishlist.domain.usecase.EditShopItemUseCase
import com.masorone.wishlist.domain.usecase.FetchShopListUseCase

class MainViewModel : ViewModel() {

    private val shopItemRepository = ShopItemRepositoryImpl

    private val fetchShopListUseCase = FetchShopListUseCase(shopItemRepository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopItemRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopItemRepository)

    private val _shopList = MutableLiveData<List<ShopItem>>()
    val shopList: LiveData<List<ShopItem>>
        get() = _shopList

    fun fetchShopList() {
        _shopList.value = fetchShopListUseCase.fetch()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.delete(shopItem)
        fetchShopList()
    }

    fun changeStateShopItem(shopItem: ShopItem) {
        editShopItemUseCase.edit(shopItem.copy(enabled = !shopItem.enabled))
        fetchShopList()
    }
}