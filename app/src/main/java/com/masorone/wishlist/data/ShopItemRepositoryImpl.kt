package com.masorone.wishlist.data

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.repository.ShopItemRepository

class ShopItemRepositoryImpl(application: Application) : ShopItemRepository {

    private val shopItemDao = ShopItemDatabase.getInstance(application).shopItemDao()

    private val mapper = ShopItemMapper()

    override suspend fun add(shopItem: ShopItem) = shopItemDao.add(mapper.mapToDb(shopItem))

    override suspend fun delete(shopItem: ShopItem) = shopItemDao.delete(shopItem.id)

    override suspend fun edit(shopItem: ShopItem) = add(shopItem)

    override fun fetch(): LiveData<List<ShopItem>> = Transformations.map(
        shopItemDao.fetch()
    ) { list ->
        mapper.mapToListOfDomain(list)
    }

    override suspend fun fetch(shopItemId: Int) = mapper.mapToDomain(shopItemDao.fetch(shopItemId))
}