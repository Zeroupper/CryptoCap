package com.example.cryptocap.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CryptoCoinDao {

    @Insert
    fun insertCryptoCoin(cryptoCoin: RoomCryptoCoin)

    @Query("SELECT * FROM CryptoCoin")
    fun getAllCryptoCoins(): LiveData<List<RoomCryptoCoin>>

    @Update
    fun updateCryptoCoin(cryptoCoin: RoomCryptoCoin): Int

    @Delete
    fun deleteCryptoCoin(cryptoCoin: RoomCryptoCoin)

}