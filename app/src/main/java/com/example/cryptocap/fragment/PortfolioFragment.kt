package com.example.cryptocap.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocap.R
import com.example.cryptocap.adapter.PortfolioAdapter
import com.example.cryptocap.databinding.FragmentPortfolioBinding
import com.example.cryptocap.helpers.NumberFormatter
import com.example.cryptocap.model.CryptoCoin
import com.example.cryptocap.model.CryptoCoinData
import com.example.cryptocap.network.NetworkManager
import com.example.cryptocap.viewmodel.CryptoCoinViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var adapter: PortfolioAdapter
    private lateinit var cryptoCoinViewModel: CryptoCoinViewModel
    private var cryptoCoinData: CryptoCoinData? = null
    private var currentList: List<CryptoCoin>? = null

    private val CHANNELID = "channel_crypto_01"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cryptoCoinViewModel = ViewModelProvider(this).get(CryptoCoinViewModel::class.java)
        createNotificationChannel()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPortfolioBinding.inflate(layoutInflater, container, false)
        binding.srlPortfolio.setOnRefreshListener {
            refreshData()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadCryptoData()

    }

    private fun initRecyclerView(cryptoCoinData: CryptoCoinData?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PortfolioAdapter(cryptoCoinData)
        cryptoCoinViewModel.allCryptoCoins?.observe(this, { cryptos ->
            adapter.submitList(cryptos)
            // this code below creates the notification, but it may be hard to recreate
            if(currentList != cryptos) {
                val percentage = 0.0001 // <- To get faster notification you change this number and maybe you succeed (check proof in documentation)
                val anyItem = adapter.currentList.find { it.price * (1.00 + percentage / 100) < getMarketPriceFromName(it.name)!! }
                if(anyItem != null) {
                    createNotification("${anyItem.name} has gained over $percentage% since you bought it at \$${NumberFormatter.correctFormat(anyItem.price)}" )
                }
            }
            currentList = cryptos
        })
        binding.recyclerView.adapter = adapter
    }

    private fun loadCryptoData() {
        binding.srlPortfolio.isRefreshing = true
        NetworkManager.getCryptoCoins()?.enqueue(object : Callback<CryptoCoinData?> {
            override fun onResponse(
                call: Call<CryptoCoinData?>,
                response: Response<CryptoCoinData?>
            ) {
                if (response.isSuccessful) {
                    cryptoCoinData = response.body()
                    initRecyclerView(cryptoCoinData)
                    binding.srlPortfolio.isRefreshing = false
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
                binding.srlPortfolio.isRefreshing = false
            }
        })
    }
    private fun refreshData() {
        loadCryptoData()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNELID,
                "CryptoCap",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Your portfolio has risen." }
            val manager : NotificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(text: String) {

        val builder = NotificationCompat.Builder(requireContext(), CHANNELID)
            .setContentTitle("CryptoCap")
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(notificationId, builder.build())
        }
    }

    private fun getMarketPriceFromName(name: String) : Double? {
        val crypto = cryptoCoinData?.data?.find { it.name.equals(name) }
        return crypto?.quote?.USD?.price
    }
}