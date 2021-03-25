package com.finalproject.presentations.employee.claim.detail

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.databinding.FragmentDetailReimbursementBinding
import com.finalproject.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class DetailReimbursementFragment : Fragment() {


    private var dataUri: Uri? = null
    private var file : File? = null
    private var uriString: String? = null


    private lateinit var binding: FragmentDetailReimbursementBinding
    private var reimburseDetail: ReimbursementResponse? = null
    private var dateClaim: String? = null
    private var myDownloadId: Long = 0 //Untuk verifikasi jika download complete
    private lateinit var viewModel : DetailReimbursementViewModel
    private var br = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if(id == myDownloadId) {
                Toast.makeText(requireContext(), "File Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it?.resultCode == Activity.RESULT_OK) {
            dataUri = it.data?.data
            file = File(dataUri?.path?.let { it1 -> uriPath(it1) })
            uriString = it.data?.data.toString()
            binding.apply {
                tvNameFile.text = "File : ${file?.name}"
                btnSeeFile.visibility = View.VISIBLE
            }

        } else {
            binding.tvNameFile.text = "Tidak Diketahui Filenya"
        }
    }
    private lateinit var loadingDialog : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            reimburseDetail = this.getParcelable(HistoryConstant.SEND_BUNDLE_DATA_REIMBURSEMENT)
            dateClaim = reimburseDetail?.dateOfClaimSubmission?.substring(0, 10)

        }
        binding = FragmentDetailReimbursementBinding.inflate(layoutInflater)
        initViewModel()
        subscribe()
        loadingDialog = LoadingDialog.build(requireContext())
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
                editButton.setOnClickListener {
                    reimburseDetail?.id?.let { it1 -> file?.let { it2 -> viewModel.uploadFile(it1, it2) } }
                }
                btnUploadFiles.setOnClickListener {
                    callChooseFileFromDevice()
                }
                btnSeeFile.setOnClickListener {
                    val bundle = bundleOf("Document" to uriString)
                    findNavController().navigate(R.id.action_detailReimbursementFragment_to_openPdfFragment, bundle)
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
        val claimFee = reimburseDetail?.claimFee?.toDouble()?.let { RupiahUtils.formatRupiah(it) }
        binding.apply {
            tvResultInputClaim.text = "${claimFee}"
            tvResultClaimDate.text = dateClaim
            tvResultDisbursementDate.text = dateDisbursement
            tvResultStartDate.text = startDate
            tvResultEndDate.text = endDate
        }
    }

    private fun setStatus() {
        if (reimburseDetail?.statusSuccess != true && reimburseDetail?.statusReject != true) {
            binding.apply {
                linearLayoutDisbursementDate.visibility = View.GONE
                tvResultClaimFee.visibility = View.GONE
                tvResultStatusOnReimbursement.text = "On Progress"
                editDateVisibile()
            }
        } else if (reimburseDetail?.statusSuccess == true) {
            binding.apply {
                val borneCost = reimburseDetail?.borneCost?.toDouble()?.let { RupiahUtils.formatRupiah(it) }
                tvTitleDetailReimbursement.text = "Detail Reimbursement Diterima"
                tvResultStatusOnReimbursement.text = "Selesai"
                tvResultClaimFee.text = "Uang Cair : ${borneCost}"
                btnDownloadFile.text = "Download Bukti Transfer"
                editDataGone()

            }
        } else if (reimburseDetail?.statusReject == true) {
            binding.apply {
                tvTitleDetailReimbursement.text = "Detail Reimbursement Ditolak"
                tvTitleDetailReimbursement.setTextColor(Color.RED)
                tvResultStatusOnReimbursement.text = "Rejected"
                linearLayoutStatusOnFinance.visibility = View.GONE
                linearLayoutStatusOnHc.visibility = View.GONE
                linearLayoutDisbursementDate.visibility = View.GONE
                btnDownloadFile.visibility = View.GONE
                tvResultClaimFee.visibility = View.GONE
                editDataGone()
            }
        }
        statusFinance()
        statusOnHC()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(DetailReimbursementViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.uploadFileLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), "Sukses Upload Ganti File", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
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
        val urlDownloadPdf = "${AppConstant.BASE_URL}/bill/files/employee-${reimburseDetail?.id}.pdf"
        Log.d("URL DOWNLOAD", urlDownloadPdf)
        val fileName = "Reimbursement Tanggal $dateClaim.pdf"
        var request = DownloadManager.Request(Uri.parse(urlDownloadPdf))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setTitle(fileName)
            .setDescription("File Reimbursement Sedang Diunduh")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        request.allowScanningByMediaScanner()
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        request.setMimeType("application/pdf")
        var dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)
        requireActivity().registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun downloadFilePdfBuktiTransfer() {
        val urlDownloadPdf = "${AppConstant.BASE_URL}/bill/files/admin-${reimburseDetail?.id}.pdf"
        val fileName = "Bukti Transfer Tanggal $dateClaim.pdf"
        var request = DownloadManager.Request(Uri.parse(urlDownloadPdf))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setTitle(fileName)
            .setDescription("File Reimbursement Sedang Diunduh")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        request.allowScanningByMediaScanner()
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        request.setMimeType("application/pdf")
        var dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)
        requireActivity().registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun callChooseFileFromDevice() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("application/pdf")
        intent = Intent.createChooser(intent, "Choose from A File")
        resultContract.launch(intent)
    }

    private fun uriPath(mypath : String) : String {
        var path = ""
        if(mypath.contains("document/raw:")){
            path = mypath.replace("/document/raw:","");
        } else {
            path = mypath
        }
        return path
    }

    override fun onPause() {
        super.onPause()
        loadingDialog.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailReimbursementFragment()
    }
}