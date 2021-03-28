package com.finalproject.utils

class HistoryConstant {

    companion object {
        val arrayFilter = arrayOf("Lihat Semua Data", "Saring Data Berdasarkan Tanggal dan Kategori", "Saring Data Berdasarkan Tanggal", "Saring Data Berdasarkan Kategori")
        const val SEND_BUNDLE_DATA_REIMBURSEMENT = "Reimbursement"
    }
}

fun main() {
    val url = "http://localhost:8081/files/employee-1504ab44-8f59-46a5-9631-1cdcc17bd591.jpeg"
    val jenisfile = substringURL(url = url)
    println(jenisfile)
}

fun substringURL(url : String) : String{
    val lengthUrl = url?.length
    val type = lengthUrl - 4
    return url.substring(type, lengthUrl)
}