package com.example.cryptocap.network

import com.example.cryptocap.model.CryptoCoinData
import retrofit2.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val coinMarketCapApi: CoinMarketCapApi

    private const val SERVICE_URL = "https://pro-api.coinmarketcap.com/"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        coinMarketCapApi = retrofit.create(CoinMarketCapApi::class.java)
    }

    fun getCryptoCoins(): Call<CryptoCoinData?>? {
        return coinMarketCapApi.getCryptoCoins()
    }
}