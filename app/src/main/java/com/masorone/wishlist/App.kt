package com.masorone.wishlist

import android.app.Application
import com.masorone.wishlist.di.DaggerAppComponent

class App : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}