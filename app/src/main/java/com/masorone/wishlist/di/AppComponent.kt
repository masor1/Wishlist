package com.masorone.wishlist.di

import android.app.Application
import com.masorone.wishlist.presentation.activity.MainActivity
import com.masorone.wishlist.presentation.fragment.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(shopItemFragment: ShopItemFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}