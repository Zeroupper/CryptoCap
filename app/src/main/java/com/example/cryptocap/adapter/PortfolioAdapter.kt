package com.example.cryptocap.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocap.R
import com.example.cryptocap.databinding.ItemCryptoCoinBinding
import com.example.cryptocap.databinding.ItemPortfolioCoinBinding
import com.example.cryptocap.helpers.NumberFormatter
import com.example.cryptocap.model.CryptoCoin
import com.example.cryptocap.model.CryptoCoinData
import java.text.DecimalFormat
import java.text.NumberFormat

class PortfolioAdapter(val cryptoCoinData : CryptoCoinData?) : ListAdapter<CryptoCoin, PortfolioAdapter.PortfolioViewHolder>(itemCallback) {

    companion object{
        object itemCallback : DiffUtil.ItemCallback<CryptoCoin>(){
            override fun areItemsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_portfolio_coin, parent, false)
        return PortfolioViewHolder(view)
    }

    override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
        val item = this.getItem(position)
        holder.bind(item)
    }

    inner class PortfolioViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemPortfolioCoinBinding.bind(itemView)
        var item: CryptoCoin? = null

        @SuppressLint("SetTextI18n")
        fun bind(newCryptoCoin: CryptoCoin?) {

            item = newCryptoCoin
            binding.coinName.text = item?.name
            binding.tvEntryPrice.text = "$" + NumberFormatter.correctFormat(item?.price)
            val marketPrice = getMarketPriceFromName(item?.name!!)
            binding.tvMarketPrice.text = "$" + NumberFormatter.correctFormat(marketPrice)
            binding.tvQuantity.text = item?.quantity.toString()
            val profit = calculateProfit(item?.price!!, marketPrice!!, item?.quantity!!)
            setTextView(profit, binding.tvProfit, "$")
        }

        @SuppressLint("SetTextI18n")
        fun setTextView(number : Double, textView : TextView, prefix : String) {
            if(number > 0) {
                textView.setTextColor(Color.parseColor("#00FF00"))
            }else {
                textView.setTextColor(Color.parseColor("#FF0000"))
            }
            textView.text = prefix + NumberFormatter.correctFormat(number)
        }



        private fun getMarketPriceFromName(name: String) : Double? {
            val crypto = cryptoCoinData?.data?.find { it.name.equals(name) }
            return crypto?.quote?.USD?.price
        }

        private fun calculateProfit(entryPrice: Double, marketPrice: Double, quantity: Double) : Double{
            return (marketPrice - entryPrice) * quantity
        }
    }
}