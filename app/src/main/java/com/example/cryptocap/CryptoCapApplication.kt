package com.example.cryptocap

import android.app.Application
import androidx.room.Room
import com.example.cryptocap.database.CryptoCoinDatabase

class CryptoCapApplication : Application() {

    companion object {
        lateinit var cryptoCoinDatabase: CryptoCoinDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        cryptoCoinDatabase = Room.databaseBuilder(
            applicationContext,
            CryptoCoinDatabase::class.java,
            "crypto_coin_database"
        ).fallbackToDestructiveMigration().build()
    }
}