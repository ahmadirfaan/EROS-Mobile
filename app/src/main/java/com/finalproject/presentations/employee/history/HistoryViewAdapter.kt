package com.finalproject.presentations.employee.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.R
import com.finalproject.data.models.reimburse.ReimbursementResponse

class HistoryViewAdapter(private val historyClickListener: HistoryClickListener) : RecyclerView.Adapter<HistoryViewHolder>() {

    private var data: List<ReimbursementResponse> = ArrayList<ReimbursementResponse>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_rv_reimbursement,
            parent, false
        )
        return HistoryViewHolder(view, historyClickListener)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val product = data[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = data.size

    fun setHistoryList(newHistoryList : List<ReimbursementResponse>) {
        this.data = newHistoryList
        notifyDataSetChanged()
    }
}