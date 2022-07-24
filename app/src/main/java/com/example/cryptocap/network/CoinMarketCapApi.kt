package com.example.cryptocap.network

import com.example.cryptocap.model.CryptoCoinData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CoinMarketCapApi {
    @Headers("X-CMC_PRO_API_KEY: ec86f398-0dcd-4e9e-8b2d-f537e7a046cb")
    @GET("v1/cryptocurrency/listings/latest")
    fun getCryptoCoins(): Call<CryptoCoinData?>?
}