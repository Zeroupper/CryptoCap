package com.example.cryptocap.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptocap.databinding.FragmentQuantityAskerBinding
import com.example.cryptocap.helpers.NumberFormatter
import com.example.cryptocap.model.CryptoCoin
import com.example.cryptocap.viewmodel.CryptoCoinViewModel
import kotlin.random.Random

class QuantityAskerFragment : DialogFragment() {
    private lateinit var binding: FragmentQuantityAskerBinding

    private var name: String? = null
    private var price: String? = null
    private lateinit var cryptoCoinViewModel : CryptoCoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_NAME)
            price = it.getString(ARG_PRICE)
        }
        cryptoCoinViewModel = ViewModelProvider(this).get(CryptoCoinViewModel::class.java)
    }

    companion object {
        private const val ARG_NAME = "argName"
        private const val ARG_PRICE = "argPrice"

        fun newInstance(name: String, price: String) = QuantityAskerFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, name)
                putString(ARG_PRICE, price)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentQuantityAskerBinding.inflate(inflater, container, false)
        dialog?.setTitle("Please fill in the quantity!")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOk.setOnClickListener {
            if(binding.etQuantity.text.isNotEmpty() && name != null && price != null) {
                val cryptoCoin = CryptoCoin(
                    id = Random.nextInt(),
                    name = name!!,
                    price = price!!.toDouble(),
                    quantity = binding.etQuantity.text.toString().toDouble(),
                )
                cryptoCoinViewModel.insert(
                    cryptoCoin
                )
                Toast.makeText(context, "You bought ${cryptoCoin.quantity} ${cryptoCoin.name} for \$${NumberFormatter.correctFormat(cryptoCoin.price)}", Toast.LENGTH_LONG).show()
            }
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}