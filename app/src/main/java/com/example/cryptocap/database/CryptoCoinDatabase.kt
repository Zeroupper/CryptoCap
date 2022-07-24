package com.example.cryptocap.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [RoomCryptoCoin::class]
)
abstract class CryptoCoinDatabase : RoomDatabase() {

    abstract fun cryptoCoinDao(): CryptoCoinDao

}