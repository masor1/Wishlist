package com.masorone.wishlist.di

import android.app.Application
import com.masorone.wishlist.data.ShopItemDao
import com.masorone.wishlist.data.ShopItemDatabase
import com.masorone.wishlist.data.ShopItemRepositoryImpl
import com.masorone.wishlist.domain.repository.ShopItemRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRepository(impl: ShopItemRepositoryImpl): ShopItemRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideShopItemDao(application: Application): ShopItemDao {
            return ShopItemDatabase.getInstance(application).shopItemDao()
        }
    }
}