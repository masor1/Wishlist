package com.masorone.wishlist.presentation.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.masorone.wishlist.data.ShopItemRepositoryImpl
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.usecase.DeleteShopItemUseCase
import com.masorone.wishlist.domain.usecase.EditShopItemUseCase
import com.masorone.wishlist.domain.usecase.FetchShopListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val shopItemRepository = ShopItemRepositoryImpl(application)

    private val fetchShopListUseCase = FetchShopListUseCase(shopItemRepository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(shopItemRepository)
    private val editShopItemUseCase = EditShopItemUseCase(shopItemRepository)

    private val _shopList = fetchShopListUseCase.fetch()
    val shopList: LiveData<List<ShopItem>>
        get() = _shopList

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.delete(shopItem)
        }
    }

    fun changeStateShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            editShopItemUseCase.edit(shopItem.copy(enabled = !shopItem.enabled))
        }
    }
}