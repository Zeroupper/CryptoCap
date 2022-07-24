package com.example.cryptocap

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cryptocap.databinding.ActivityDetailsBinding
import com.example.cryptocap.fragment.QuantityAskerFragment
import java.text.DecimalFormat

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    private var name : String? = null
    private var price : String? = null

    companion object {
        const val EXTRA_COIN_NAME = "default coin"
        const val EXTRA_MARKETCAP = "default marketcap"
        const val EXTRA_VOLUME_24H = "default volume 24h"
        const val EXTRA_CHANGE_24H= "default change 24h"
        const val EXTRA_PRICE = "default price"
        const val EXTRA_CMC_RANK = "default rank"
        const val EXTRA_CIRCULATING_SUPPLY = "default circulating supply"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = intent.getStringExtra(EXTRA_COIN_NAME)
        binding.tvCoinName.text = name
        binding.tvCoinRank.text = "#" + intent.getStringExtra(EXTRA_CMC_RANK)
        binding.tvMarketCap.text =  "\$" + correctStringFormat(intent.getStringExtra(EXTRA_MARKETCAP)!!.toDouble())
        binding.tvHourlyVolume.text=  "\$" + correctStringFormat(intent.getStringExtra(EXTRA_VOLUME_24H)!!.toDouble())
        binding.tvHourlyChange.text = correctStringFormat(intent.getStringExtra(EXTRA_CHANGE_24H)!!.toDouble()) + "%"
        price = intent.getStringExtra(EXTRA_PRICE)
        binding.tvPrice.text =  "\$" + correctStringFormat(price!!.toDouble())
        binding.tvCirculatingSupply.text = correctStringFormat(intent.getStringExtra(EXTRA_CIRCULATING_SUPPLY)!!.toDouble())

        binding.buyButton.setOnClickListener {
            val buyCryptoCoinFragment = QuantityAskerFragment.newInstance(
                name = name!!,
                price = price!!
            )
            buyCryptoCoinFragment.show(supportFragmentManager, "TAG")

        }

        binding.sellButton.setOnClickListener {
            Toast.makeText(this, "WHAT ARE YOU PAPER HANDS??🤢 NEVER SELL CRYPTO. ONLY DIAMOND HANDS!!💎💎🤑", Toast.LENGTH_LONG).show()

        }
    }

    private fun correctStringFormat(number : Double?) : String? {
        val formatter = DecimalFormat("#,##0.00")
        if(number != null) {
            return (formatter.format(number))
        }
        return null
    }
}