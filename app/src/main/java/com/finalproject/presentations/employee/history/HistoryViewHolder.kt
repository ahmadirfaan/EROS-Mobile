package com.finalproject.presentations.employee.history

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.databinding.LayoutRvReimbursementBinding

class HistoryViewHolder(private val view: View, private val historyClickListener: HistoryClickListener) : RecyclerView.ViewHolder(view) {

    private val binding = LayoutRvReimbursementBinding.bind(view)

    fun bind(reimbursement: ReimbursementResponse) {
        binding.apply {
            tvNameEmployee.text = "Nama : ${reimbursement?.employeeId?.fullname}"
            tvDateClaim.text = "Tanggal Klaim : ${reimbursement?.dateOfClaimSubmission?.substring(0,10)}"
            when (reimbursement?.categoryId?.id) {
                "1" -> tvCategoryReimbursement.text = "Kategori : Kacamata"
                "2" -> tvCategoryReimbursement.text = "Kategori : Pelatihan"
                "3" -> tvCategoryReimbursement.text = "Kategori : Melahirkan"
                "4" -> tvCategoryReimbursement.text = "Kategori : Perjalanan Dinas"
                "5" -> tvCategoryReimbursement.text = "Kategori : Asuransi"
            }
            if (reimbursement?.statusSuccess == false && reimbursement?.statusReject == true) {
                tvStatusReject.text = "Ditolak"
                tvStatusReject.setTextColor(Color.RED)
                linearLayoutReject.visibility = View.VISIBLE
                linearLayoutSuccess.visibility = View.GONE
                tvDateDisbursement.visibility = View.GONE
            } else if (reimbursement?.statusSuccess == true && reimbursement?.statusReject == false) {
                tvStatusSuccess.text = "Selesai"
                linearLayoutReject.visibility = View.GONE
                linearLayoutSuccess.visibility = View.VISIBLE
                tvDateDisbursement.visibility = View.VISIBLE
            } else if (reimbursement?.statusSuccess == false && reimbursement?.statusReject == false) {
                tvStatusReject.text = "Tidak Ditolak"
                tvStatusSuccess.text = "Masih Diproses"
                linearLayoutSuccess.visibility = View.VISIBLE
                tvDateDisbursement.visibility = View.GONE
            }
            val stringDisbursement = reimbursement?.disbursementDate?.substring(0, 10)
            tvDateDisbursement.text = "Tanggal Pencairan : $stringDisbursement"
            btnDetailReimbursement.setOnClickListener {
                historyClickListener.onDetail(reimbursement)
            }
        }

    }
}