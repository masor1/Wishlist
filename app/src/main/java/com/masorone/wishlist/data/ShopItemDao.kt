package com.masorone.wishlist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopItemDao {

    @Query("SELECT * FROM shop_items")
    fun fetch(): LiveData<List<ShopItemDb>>

    @Query("SELECT * FROM shop_items WHERE id=:shopItemId LIMIT 1")
    suspend fun fetch(shopItemId: Int): ShopItemDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(shopItemDb: ShopItemDb)

    @Query("DELETE FROM shop_items WHERE id =:shopItemId")
    suspend fun delete(shopItemId: Int)
}