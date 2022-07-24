package com.example.cryptocap.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocap.R
import com.example.cryptocap.databinding.ItemCryptoCoinBinding
import com.example.cryptocap.model.Data

class CryptoCoinAdapter(private val listener: OnCryptoCoinSelectedListener) : RecyclerView.Adapter<CryptoCoinAdapter.CryptoCoinViewHolder>() {

    private val cryptoCoins: MutableList<Data> = ArrayList()

    interface OnCryptoCoinSelectedListener {
        fun onCryptoCoinSelected(crypto: Data?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoCoinViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_crypto_coin, parent, false)
        return CryptoCoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoCoinViewHolder, position: Int) {
        val item = cryptoCoins[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = cryptoCoins.size

    fun addCryptoCoin(newCryptoCoin: Data) {
        cryptoCoins.add(newCryptoCoin)
        notifyItemInserted(cryptoCoins.size - 1)
    }

    fun clearAllCryptoCoins() {
        cryptoCoins.clear()
    }

    inner class CryptoCoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemCryptoCoinBinding.bind(itemView)
        var item: Data? = null

        init {
            binding.root.setOnClickListener { listener.onCryptoCoinSelected(item) }
        }

        @SuppressLint("SetTextI18n")
        fun bind(newCryptoCoin: Data?) {

            item = newCryptoCoin
            binding.symbol.text = item?.symbol.toString()
            binding.name.text = "  |  ${item?.name}"
            var formattedNumber = Math.round(item?.quote?.USD?.price!! * 100.0) / 100.0
            binding.price.text = "\$${formattedNumber}"

            formattedNumber = Math.round(item?.quote?.USD?.percent_change_1h!! * 100.0) / 100.0
            setTextView(formattedNumber, binding.hourly, "1hr: ")
            formattedNumber = Math.round(item?.quote?.USD?.percent_change_24h!! * 100.0) / 100.0
            setTextView(formattedNumber, binding.daily, "1d: ")
            formattedNumber = Math.round(item?.quote?.USD?.percent_change_30d!! * 100.0) / 100.0
            setTextView(formattedNumber, binding.monthly, "30d: ")
        }

        @SuppressLint("SetTextI18n")
        fun setTextView(number : Double, textView : TextView, prefix : String) {
            if(number > 0) {
                textView.setTextColor(Color.parseColor("#00FF00"))
            }else {
                textView.setTextColor(Color.parseColor("#FF0000"))
            }
            textView.text = prefix + "${number}%"
        }
    }
}