package com.example.cryptocap.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.cryptocap.database.CryptoCoinDao
import com.example.cryptocap.database.RoomCryptoCoin
import com.example.cryptocap.model.CryptoCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val cryptoCoinDao: CryptoCoinDao) {

    fun getAllCryptoCoins(): LiveData<List<CryptoCoin>> {
        return cryptoCoinDao.getAllCryptoCoins()
            .map {roomCryptoCoins ->
                roomCryptoCoins.map {roomCryptoCoin->
                    roomCryptoCoin.toDomainModel() }
            }
    }

    suspend fun insert(cryptoCoin: CryptoCoin) = withContext(Dispatchers.IO) {
        cryptoCoinDao.insertCryptoCoin(cryptoCoin.toRoomModel())
    }

    suspend fun update(cryptoCoin: CryptoCoin) = withContext(Dispatchers.IO) {
        cryptoCoinDao.updateCryptoCoin(cryptoCoin.toRoomModel())
    }
    suspend fun delete(cryptoCoin: CryptoCoin) = withContext(Dispatchers.IO) {
        cryptoCoinDao.deleteCryptoCoin(cryptoCoin.toRoomModel())
    }

    private fun RoomCryptoCoin.toDomainModel(): CryptoCoin {
        return CryptoCoin(
            id = id,
            name = name,
            price = price,
            quantity = quantity,
        )
    }

    private fun CryptoCoin.toRoomModel(): RoomCryptoCoin {
        return RoomCryptoCoin(
            name = name,
            price = price,
            quantity = quantity,
        )
    }
}
