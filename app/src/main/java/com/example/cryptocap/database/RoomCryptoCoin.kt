package com.example.cryptocap.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CryptoCoin")
data class RoomCryptoCoin(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Double,

)