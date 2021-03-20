package com.finalproject.presentations.employee.claim.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.R
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.databinding.FragmentDetailReimbursementBinding
import com.finalproject.utils.HistoryConstant


class DetailReimbursementFragment : Fragment() {

    private lateinit var binding : FragmentDetailReimbursementBinding
    private var reimburseDetail : ReimbursementResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            reimburseDetail = this.getParcelable(HistoryConstant.SEND_BUNDLE_DATA_REIMBURSEMENT)
        }
        binding  = FragmentDetailReimbursementBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(reimburseDetail != null) {
            binding.apply {
                setTitleCategory()
                setDate()
                setStatus()
            }
        }
    }

    private fun setTitleCategory() {
        binding.apply {
            when (reimburseDetail?.categoryId?.id) {
                "1" -> {
                    tvTitleCategoryReimbursement.text = "Category : Kacamata"
                    hidingStartAndEndDate()
                }
                "2" -> tvTitleCategoryReimbursement.text = "Category : Pelatihan"
                "3" -> {
                    tvTitleCategoryReimbursement.text = "Category : Melahirkan"
                    hidingStartAndEndDate()
                }
                "4" -> tvTitleCategoryReimbursement.text = "Category : Perjalanan Dinas"
                "5" -> {
                    tvTitleCategoryReimbursement.text = "Category : Asuransi"
                    hidingStartAndEndDate()
                }
            }
        }
    }

    private fun hidingStartAndEndDate() {
        binding.apply {
            linearLayoutStartDate.visibility = View.GONE
            linearLayoutEndDate.visibility = View.GONE
        }
    }

    private fun setDate() {
        val dateDisbursement = reimburseDetail?.disbursementDate?.substring(0, 10)
        val dateClaim = reimburseDetail?.dateOfClaimSubmission?.substring(0,10)
        val startDate = reimburseDetail?.startDate?.substring(0,10)
        val endDate = reimburseDetail?.endDate?.substring(0,10)
        binding.apply {
            tvResultClaimDate.text = dateClaim
            tvResultDisbursementDate.text = dateDisbursement
            tvResultStartDate.text = startDate
            tvResultEndDate.text = endDate
        }
    }

    private fun setStatus() {
        if(reimburseDetail?.statusSuccess != true) {
            binding.apply {
                linearLayoutDisbursementDate.visibility = View.GONE
                tvResultClaimFee.visibility = View.GONE
                tvResultStatusOnReimbursement.text = "On Progress"
            }
        } else if(reimburseDetail?.statusSuccess == true) {
            binding.apply {
                tvResultStatusOnReimbursement.text = "Selesai"
                tvResultClaimFee.text = "Rp ${reimburseDetail?.claimFee}"
                editButton.visibility = View.GONE
            }
        } else if(reimburseDetail?.statusReject == true) {
            binding.apply {
                tvResultStatusOnReimbursement.text = "Rejected"
                tvResultClaimFee.visibility = View.GONE
            }
        }
        statusFinance()
        statusOnHC()
    }

    private fun statusFinance() {
        if(reimburseDetail?.statusOnFinance == true) {
            binding.tvResultStatusOnFinance.text = "Selesai"
        } else {
            binding.tvResultStatusOnFinance.text = "On Progress"
        }
    }

    private fun statusOnHC() {
        if(reimburseDetail?.statusOnHc == true) {
            binding.tvResultStatusOnHc.text = "Selesai"
        } else {
            binding.tvResultStatusOnHc.text = "On Progress"
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailReimbursementFragment()
    }
}