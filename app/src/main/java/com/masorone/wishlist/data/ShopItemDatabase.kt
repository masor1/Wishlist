package com.masorone.wishlist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDb::class], version = 1, exportSchema = false)
abstract class ShopItemDatabase : RoomDatabase() {

    abstract fun shopItemDao(): ShopItemDao

    companion object {

        private var INSTANCE: ShopItemDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "wishlist.db"

        fun getInstance(application: Application): ShopItemDatabase {
            INSTANCE?.let { instance ->
                return instance
            }

            synchronized(LOCK) {
                INSTANCE?.let { instance ->
                    return instance
                }

                val db = Room.databaseBuilder(
                    application,
                    ShopItemDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = db

                return db
            }
        }
    }
}