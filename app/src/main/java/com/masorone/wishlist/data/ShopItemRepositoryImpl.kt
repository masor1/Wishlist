package com.masorone.wishlist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.masorone.wishlist.domain.model.ShopItem
import com.masorone.wishlist.domain.repository.ShopItemRepository
import javax.inject.Inject

class ShopItemRepositoryImpl @Inject constructor(
    private val shopItemDao: ShopItemDao,
    private val mapper: ShopItemMapper
) : ShopItemRepository {

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