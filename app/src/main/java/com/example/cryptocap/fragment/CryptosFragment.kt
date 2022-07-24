package com.example.cryptocap.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocap.DetailsActivity
import com.example.cryptocap.adapter.CryptoCoinAdapter
import com.example.cryptocap.databinding.FragmentCryptosBinding
import com.example.cryptocap.model.CryptoCoinData
import com.example.cryptocap.model.CryptoCoinDataHodler
import com.example.cryptocap.model.Data
import com.example.cryptocap.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptosFragment : Fragment(),
    CryptoCoinAdapter.OnCryptoCoinSelectedListener,
    CryptoCoinDataHodler {
    private lateinit var binding: FragmentCryptosBinding
    private lateinit var adapter: CryptoCoinAdapter
    private var cryptoCoinData: CryptoCoinData? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCryptosBinding.inflate(layoutInflater, container, false)
        binding.srlCryptoCoins.setOnRefreshListener {
            refreshData()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadCryptoData()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CryptoCoinAdapter(this)
        if(cryptoCoinData != null) {
            for (data in cryptoCoinData!!.data)
                adapter.addCryptoCoin(data)
        }
        binding.recyclerView.adapter = adapter
    }

    override fun onCryptoCoinSelected(crypto: Data?) {
        val showDetailsIntent = Intent()
        context?.let { showDetailsIntent.setClass(it, DetailsActivity::class.java) }
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_COIN_NAME, crypto?.name)
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_CMC_RANK, crypto?.cmc_rank.toString())
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_MARKETCAP, crypto?.quote?.USD?.fully_diluted_market_cap.toString())
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_VOLUME_24H, crypto?.quote?.USD?.volume_24h.toString())
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_CHANGE_24H, crypto?.quote?.USD?.percent_change_24h.toString())
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_PRICE, crypto?.quote?.USD?.price.toString())
        showDetailsIntent.putExtra(DetailsActivity.EXTRA_CIRCULATING_SUPPLY, crypto?.circulating_supply.toString())
        startActivity(showDetailsIntent)
    }

    override fun getCryptoCoinData(): CryptoCoinData? {
        return cryptoCoinData
    }

    private fun loadCryptoData() {
        binding.srlCryptoCoins.isRefreshing = true
        NetworkManager.getCryptoCoins()?.enqueue(object : Callback<CryptoCoinData?> {
            override fun onResponse(
                call: Call<CryptoCoinData?>,
                response: Response<CryptoCoinData?>
            ) {
                if (response.isSuccessful) {
                    displayCryptoCoinData(response.body())
                    initRecyclerView()
                    binding.srlCryptoCoins.isRefreshing = false
                } else {
                    Toast.makeText(context, "Error: " + response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<CryptoCoinData?>,
                throwable: Throwable
            ) {
                throwable.printStackTrace()
                Toast.makeText(context, "Network request error occured", Toast.LENGTH_LONG).show()
                binding.srlCryptoCoins.isRefreshing = false
            }
        })
    }

    private fun displayCryptoCoinData(receivedCryptoCoinData: CryptoCoinData?) {
        cryptoCoinData = receivedCryptoCoinData
    }

    private fun refreshData() {
        adapter.clearAllCryptoCoins()
        loadCryptoData()
    }
}