package com.masorone.wishlist.presentation.fragment

import android.app.Application
import androidx.lifecycle.*
import com.masorone.wishlist.data.ShopItemRepositoryImpl
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.usecase.AddShopItemUseCase
import com.masorone.wishlist.domain.usecase.EditShopItemUseCase
import com.masorone.wishlist.domain.usecase.FetchShopItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopItemRepositoryImpl(application)

    private val fetchShopItemUseCase = FetchShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    fun fetch(shopItemId: Int) {
        viewModelScope.launch {
            _shopItem.value = fetchShopItemUseCase.fetch(shopItemId)
        }
    }

    fun add(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                addShopItemUseCase.add(ShopItem(name, count, true))
                finishWork()
            }
        }
    }

    fun edit(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let { shopItem ->
                viewModelScope.launch {
                    editShopItemUseCase.edit(shopItem.copy(name = name, count = count))
                    finishWork()
                }
            }
        }
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}