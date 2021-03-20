package com.finalproject.presentations.employee.history

import com.finalproject.data.models.reimburse.ReimbursementResponse

interface HistoryClickListener {
    fun onDetail(reimburse: ReimbursementResponse)
}