package com.example.cryptocap.helpers

import java.text.DecimalFormat

object NumberFormatter {
    fun correctFormat(number : Double?) : String? {
        val formatter = DecimalFormat("#,##0.00")
        if(number != null) {
            return (formatter.format(number))
        }
        return null
    }
}