package com.finalproject.utils

import java.text.NumberFormat
import java.util.*

class RupiahUtils {

    companion object {
        fun formatRupiah(number : Double) : String {
            val localeId = Locale("IND", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeId)
            val formatRupiah = numberFormat.format(number)
            val split = formatRupiah.split(",")
            val length = split[0].length
            return split[0].substring(0,2)+". "+split[0].substring(2,length)
        }
    }
}