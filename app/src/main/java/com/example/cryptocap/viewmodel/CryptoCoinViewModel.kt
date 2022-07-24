package com.example.cryptocap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocap.CryptoCapApplication
import com.example.cryptocap.model.CryptoCoin
import com.example.cryptocap.repository.Repository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class CryptoCoinViewModel : ViewModel() {
    private val repository: Repository

    val allCryptoCoins: LiveData<List<CryptoCoin>>?

    init {
        val cryptoCoinDao = CryptoCapApplication.cryptoCoinDatabase.cryptoCoinDao()
        repository = Repository(cryptoCoinDao)
        allCryptoCoins = repository.getAllCryptoCoins()
    }

    fun insert(cryptoCoin: CryptoCoin) = viewModelScope.launch {
        repository.insert(cryptoCoin)
    }

    fun update(cryptoCoin: CryptoCoin) = viewModelScope.launch {
        repository.update(cryptoCoin)
    }

    fun delete(cryptoCoin: CryptoCoin) = viewModelScope.launch {
        repository.delete(cryptoCoin)
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}
