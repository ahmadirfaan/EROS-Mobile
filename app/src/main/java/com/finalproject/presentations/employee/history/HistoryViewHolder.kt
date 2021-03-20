package com.finalproject.presentations.employee.history

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.databinding.LayoutRvReimbursementBinding

class HistoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = LayoutRvReimbursementBinding.bind(view)

    fun bind(reimbursement: ReimbursementResponse) {
        binding.apply {
            tvNameEmployee.text = "Name : ${reimbursement?.employeeId?.fullname}"
            when (reimbursement?.categoryId?.id) {
                "1" -> tvCategoryReimbursement.text = "Category : Kacamata"
                "2" -> tvCategoryReimbursement.text = "Category : Pelatihan"
                "3" -> tvCategoryReimbursement.text = "Category : Melahirkan"
                "4" -> tvCategoryReimbursement.text = "Category : Perjalanan Dinas"
                "5" -> tvCategoryReimbursement.text = "Category : Asuransi"
            }
            if (reimbursement?.statusSuccess == false && reimbursement?.statusReject == true) {
                tvStatusReject.text = "Ditolak"
                tvStatusReject.setTextColor(Color.RED)
                linearLayoutReject.visibility = View.VISIBLE
                linearLayoutSuccess.visibility = View.GONE
                tvDateDisbursement.visibility = View.GONE
            } else if (reimbursement?.statusSuccess == true && reimbursement?.statusReject == false) {
                tvStatusSuccess.text = "Selesai dan Cair"
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
        }

    }
}