package com.masorone.wishlist.presentation.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.usecase.DeleteShopItemUseCase
import com.masorone.wishlist.domain.usecase.EditShopItemUseCase
import com.masorone.wishlist.domain.usecase.FetchShopListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    fetchShopListUseCase: FetchShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase
) : ViewModel() {

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