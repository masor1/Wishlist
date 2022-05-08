package com.masorone.wishlist.di

import androidx.lifecycle.ViewModel
import com.masorone.wishlist.presentation.activity.MainViewModel
import com.masorone.wishlist.presentation.fragment.ShopItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel(impl: ShopItemViewModel): ViewModel
}