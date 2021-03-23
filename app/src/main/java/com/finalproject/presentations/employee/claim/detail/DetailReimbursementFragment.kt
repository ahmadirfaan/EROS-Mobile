package com.finalproject.presentations.employee.claim.detail

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.databinding.FragmentDetailReimbursementBinding
import com.finalproject.utils.AppConstant
import com.finalproject.utils.HistoryConstant


class DetailReimbursementFragment : Fragment() {

    private lateinit var binding: FragmentDetailReimbursementBinding
    private var reimburseDetail: ReimbursementResponse? = null
    private var dateClaim: String? = null
    private var myDownloadId: Long = 0
    private var br = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if(id == myDownloadId) {
                Toast.makeText(requireContext(), "File Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            reimburseDetail = this.getParcelable(HistoryConstant.SEND_BUNDLE_DATA_REIMBURSEMENT)
            dateClaim = reimburseDetail?.dateOfClaimSubmission?.substring(0, 10)

        }
        binding = FragmentDetailReimbursementBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (reimburseDetail != null) {
            binding.apply {
                setTitleCategory()
                setDate()
                setStatus()
                btnDownloadFile.setOnClickListener {
                    if(reimburseDetail?.statusSuccess == true) {
                        downloadFilePdfBuktiTransfer()
                    } else {
                        downloadFilePdfDataEmployee()
                    }
//                    val bundle = bundleOf("StringURLPDF" to reimburseDetail?.id)
//                    findNavController().navigate(R.id.action_detailReimbursementFragment_to_webViewPdfFragment, bundle)
                }
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
        val startDate = reimburseDetail?.startDate?.substring(0, 10)
        val endDate = reimburseDetail?.endDate?.substring(0, 10)
        val inputClaim = reimburseDetail?.claimFee
        binding.apply {
            tvResultInputClaim.text = "Rp $inputClaim"
            tvResultClaimDate.text = dateClaim
            tvResultDisbursementDate.text = dateDisbursement
            tvResultStartDate.text = startDate
            tvResultEndDate.text = endDate
        }
    }

    private fun setStatus() {
        if (reimburseDetail?.statusSuccess != true) {
            binding.apply {
                linearLayoutDisbursementDate.visibility = View.GONE
                tvResultClaimFee.visibility = View.GONE
                tvResultStatusOnReimbursement.text = "On Progress"
                editDateVisibile()
            }
        } else if (reimburseDetail?.statusSuccess == true) {
            binding.apply {
                tvTitleDetailReimbursement.text = "Detail Reimbursement Diterima"
                tvResultStatusOnReimbursement.text = "Selesai"
                tvResultClaimFee.text = "Uang Cair : Rp ${reimburseDetail?.borneCost}"
                btnDownloadFile.text = "Download Bukti Transfer"
                editDataGone()

            }
        } else if (reimburseDetail?.statusReject == true) {
            binding.apply {
                tvTitleDetailReimbursement.text = "Detail Reimbursement Ditolak"
                tvResultStatusOnReimbursement.text = "Rejected"
                tvResultClaimFee.visibility = View.GONE
                editDataGone()
            }
        }
        statusFinance()
        statusOnHC()
    }

    private fun editDataGone() {
        binding.apply {
            btnUploadFiles.visibility = View.GONE
            tvWarningFile.visibility = View.GONE
            tvNameFile.visibility = View.GONE
            editButton.visibility = View.GONE
        }
    }

    private fun editDateVisibile() {
        binding.apply {
            btnUploadFiles.visibility = View.VISIBLE
            tvWarningFile.visibility = View.VISIBLE
            tvNameFile.visibility = View.VISIBLE
            editButton.visibility = View.VISIBLE
        }
    }

    private fun statusFinance() {
        if (reimburseDetail?.statusOnFinance == true) {
            binding.tvResultStatusOnFinance.text = "Selesai"
        } else {
            binding.tvResultStatusOnFinance.text = "On Progress"
        }
    }

    private fun statusOnHC() {
        if (reimburseDetail?.statusOnHc == true) {
            binding.tvResultStatusOnHc.text = "Selesai"
        } else {
            binding.tvResultStatusOnHc.text = "On Progress"
        }
    }

    private fun downloadFilePdfDataEmployee() {
        val urlDownloadPdf = "${AppConstant.BASE_URL}/bill/files/${reimburseDetail?.id}.pdf"
        Log.d("URL DOWNLOAD", urlDownloadPdf)
        var request = DownloadManager.Request(Uri.parse(urlDownloadPdf))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setTitle("Reimbursement Tanggal $dateClaim.pdf")
            .setDescription("File Reimbursement Sedang Diunduh")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        request.allowScanningByMediaScanner()
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")
        request.setMimeType("application/pdf")
        var dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)
        requireActivity().registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun downloadFilePdfBuktiTransfer() {
        val urlDownloadPdf = "${AppConstant.BASE_URL}/bill/files/${reimburseDetail?.id}.pdf"
        var request = DownloadManager.Request(Uri.parse(urlDownloadPdf))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setTitle("Bukti Transfer Tanggal $dateClaim.pdf")
            .setDescription("File Reimbursement Sedang Diunduh")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        request.allowScanningByMediaScanner()
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${System.currentTimeMillis()}")
        request.setMimeType("application/pdf")
        var dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)
        requireActivity().registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailReimbursementFragment()
    }
}