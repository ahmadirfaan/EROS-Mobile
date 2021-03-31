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
import com.finalproject.data.models.reimburse.AddReimbursementRequest
import com.finalproject.data.models.reimburse.BillResponse
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.databinding.FragmentDetailReimbursementBinding
import com.finalproject.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class DetailReimbursementFragment : Fragment() {


    private var dataUri: Uri? = null
    private var file: File? = null
    private var uriString: String? = null
    private var urlDownloadFile: String? = null


    private lateinit var binding: FragmentDetailReimbursementBinding
    private var reimburseDetail: ReimbursementResponse? = null
    private var dateClaim: String? = null
    private var myDownloadId: Long = 0 //Untuk verifikasi jika download complete
    private lateinit var viewModel: DetailReimbursementViewModel
    private var br = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (id == myDownloadId) {
                Toast.makeText(requireContext(), "File Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it?.resultCode == Activity.RESULT_OK) {
            dataUri = it.data?.data
            Log.d("DATA URI", dataUri.toString())
            Log.d("DATA PATH URI", dataUri?.path.toString())
            file = File(dataUri?.path?.let { it1 -> uriPath(it1) })
            Log.d("FILE OBJECT", file.toString())
            Log.d("FILE PATH", file!!.path)
            Log.d("FILE EXTENSION", file!!.extension)
            uriString = it.data?.data.toString()
            binding.apply {
                tvNameFile.text = "File : ${file?.name}"
                btnSeeFile.visibility = View.VISIBLE
            }

        } else {
            binding.tvNameFile.text = "Tidak Diketahui Filenya"
        }
    }
    private lateinit var loadingDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            reimburseDetail = this.getParcelable(HistoryConstant.SEND_BUNDLE_DATA_REIMBURSEMENT)
            dateClaim = reimburseDetail?.dateOfClaimSubmission?.substring(0, 10)
        }
        binding = FragmentDetailReimbursementBinding.inflate(layoutInflater)
        initViewModel()
        subscribe()
        if(reimburseDetail?.statusSuccess == true) {
            reimburseDetail?.id?.let { it1 -> viewModel.getURLDownloadFileAdmin(it1) }
        } else {
            reimburseDetail?.id?.let { it1 -> viewModel.getURLDownloadFileEmployee(it1) }

        }
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
                    if (reimburseDetail?.statusSuccess == true) {
                        downloadFilePdfBuktiTransfer()
                    } else {
                        downloadFilePdfDataEmployee()
                    }
//                    val bundle = bundleOf("StringURLPDF" to reimburseDetail?.id)
//                    findNavController().navigate(R.id.action_detailReimbursementFragment_to_webViewPdfFragment, bundle)
                }
                editButton.setOnClickListener {
                    val formatFile = arrayListOf<String>("jpg","pdf","png","jpeg")
                    val fileSizeInMegaByte = file?.length()?.div(1024)?.div(1024)
                    Log.d("FiLE SIZE IN MEGABYte", fileSizeInMegaByte!!.toString())
                    val tvNameFile = tvNameFile.text.toString()
                    if (tvNameFile.isBlank() || tvNameFile.equals("Tidak Diketahui Filenya")) {
                        Toast.makeText(requireContext(), "File Tidak Benar", Toast.LENGTH_SHORT).show()
                    } else if (fileSizeInMegaByte > 5) {
                        Toast.makeText(requireContext(), "Ukuran Maksimal 5MB", Toast.LENGTH_SHORT).show()
                    } else if (!formatFile.contains(file?.extension)) {
                        Toast.makeText(requireContext(), "File Yang Anda Masukkan tidak didukung", Toast.LENGTH_SHORT).show()
                    } else {
                        reimburseDetail?.id?.let { it1 -> file?.let { it2 -> viewModel.uploadFile(it1, it2) } }
                    }
                }
                btnUploadFiles.setOnClickListener {
                    callChooseFileFromDevice()
                }
                btnSeeFile.setOnClickListener {
                    if (file?.extension == "pdf") {
                        val bundle = bundleOf("Document" to uriString)
                        findNavController().navigate(R.id.action_detailReimbursementFragment_to_openPdfFragment, bundle)
                    } else if (file?.extension == "jpg" || file?.extension == "png" || file?.extension == "jpeg") {
                        val bundle = bundleOf("Image" to uriString)
                        findNavController().navigate(R.id.action_detailReimbursementFragment_to_openPdfFragment, bundle)
                    } else {
                        Toast.makeText(requireContext(), "Pilih File Yang Benar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setTitleCategory() {
        binding.apply {
            when (reimburseDetail?.categoryId?.id) {
                "1" -> {
                    tvTitleCategoryReimbursement.text = "Kategori : Kacamata"
                    hidingStartAndEndDate()
                }
                "2" -> tvTitleCategoryReimbursement.text = "Kategori : Pelatihan"
                "3" -> {
                    tvTitleCategoryReimbursement.text = "Kategori : Melahirkan"
                    hidingStartAndEndDate()
                }
                "4" -> tvTitleCategoryReimbursement.text = "Kategori : Perjalanan Dinas"
                "5" -> {
                    tvTitleCategoryReimbursement.text = "Kategori : Asuransi"
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
                tvResultStatusOnReimbursement.text = "Masih Diproses"
                editDateVisibile()
            }
        } else if (reimburseDetail?.statusSuccess == true) {
            binding.apply {
                val borneCost = reimburseDetail?.borneCost?.toDouble()?.let { RupiahUtils.formatRupiah(it) }
                tvTitleDetailReimbursement.text = "Detail Reimbursement Diterima"
                tvResultStatusOnReimbursement.text = "Selesai"
                tvResultClaimFee.text = "Uang Cair : ${borneCost}"
                btnDownloadFile.text = "Unduh Bukti Transfer"
                editDataGone()

            }
        } else if (reimburseDetail?.statusReject == true) {
            binding.apply {
                tvTitleDetailReimbursement.text = "Detail Reimbursement Ditolak"
                tvTitleDetailReimbursement.setTextColor(Color.RED)
                tvResultStatusOnReimbursement.text = "Ditolak"
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
        viewModel.getURLFileLiveDataEmployee.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val data = it?.data as BillResponse
                    urlDownloadFile = data?.data?.url
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.getURLFileLiveDataAdmin.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val data = it?.data as BillResponse
                    urlDownloadFile = data?.data?.url
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
            binding.tvResultStatusOnFinance.text = "Masih Diproses"
        }
    }

    private fun statusOnHC() {
        if (reimburseDetail?.statusOnHc == true) {
            binding.tvResultStatusOnHc.text = "Selesai"
        } else {
            binding.tvResultStatusOnHc.text = "Masih Diproses"
        }
    }

    private fun downloadFilePdfDataEmployee() {
        var fileName = "Reimbursement Tanggal $dateClaim"
        urlDownloadFile = urlDownloadFile?.replace("http://localhost:8081", AppConstant.BASE_URL)
        Log.d("URL DOWNLOAD", urlDownloadFile.toString())
        var request = DownloadManager.Request(Uri.parse(urlDownloadFile))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setTitle(fileName)
            .setDescription("File Reimbursement Sedang Diunduh")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        request.allowScanningByMediaScanner()
        val lengthUrl = urlDownloadFile?.length
        val type = lengthUrl?.minus(4)
        val jenisFile = type?.let { urlDownloadFile?.substring(it, lengthUrl) }
        if (jenisFile?.equals(".pdf", true) == true) {
            Log.d("MASUK LOG SET MIME TYPE", "MASUK DISINI PDF")
            request.setMimeType("application/pdf")
            fileName += ".pdf"
        } else if (jenisFile?.equals(".jpg", true) == true) {
            request.setMimeType("image/jpg")
            fileName += ".jpg"
        } else if (jenisFile?.equals("jpeg", true) == true) {
            request.setMimeType("image/jpeg")
            fileName += ".jpeg"
        } else if (jenisFile?.equals(".png", true) == true) {
            request.setMimeType("image/png")
            fileName += ".png"
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        var dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)
        requireActivity().registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun downloadFilePdfBuktiTransfer() {
        var fileName = "Bukti Transfer Tanggal $dateClaim"
        urlDownloadFile = urlDownloadFile?.replace("http://localhost:8081", AppConstant.BASE_URL)
        var request = DownloadManager.Request(Uri.parse(urlDownloadFile))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setTitle(fileName)
            .setDescription("File Reimbursement Sedang Diunduh")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
        request.allowScanningByMediaScanner()
        val lengthUrl = urlDownloadFile?.length
        val type = lengthUrl?.minus(4)
        val jenisFile = type?.let { urlDownloadFile?.substring(it, lengthUrl) }
        Log.d("JENIS FILE", jenisFile!!)
        if (jenisFile?.equals(".pdf", true) == true) {
            Log.d("MASUK LOG SET MIME TYPE", "MASUK DISINI PDF")
            request.setMimeType("application/pdf")
            fileName += ".pdf"
        } else if (jenisFile?.equals(".jpg", true) == true) {
            request.setMimeType("image/jpg")
            fileName += ".jpg"
        } else if (jenisFile?.equals("jpeg", true) == true) {
            request.setMimeType("image/jpeg")
            fileName += ".jpeg"
        } else if (jenisFile?.equals(".png", true) == true) {
            request.setMimeType("image/png")
            fileName += ".png"
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        var dm = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        myDownloadId = dm.enqueue(request)
        requireActivity().registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

//    private fun setMimeType(downloadRequest: DownloadManager.Request, filename : String) {
//        val lengthUrl = urlDownloadFile?.length
//        val type = lengthUrl?.minus(4)
//        val jenisFile = type?.let { urlDownloadFile?.substring(it, lengthUrl) }
//        Log.d("JENIS FILE", jenisFile!!)
//        if (jenisFile?.equals(".pdf", true) == true) {
//            Log.d("MASUK LOG SET MIME TYPE", "MASUK DISINI PDF")
//            downloadRequest.setMimeType("application/pdf")
//        } else if (jenisFile?.equals(".jpg", true) == true) {
//            downloadRequest.setMimeType("image/jpg")
//        } else if (jenisFile?.equals("jpeg", true) == true) {
//            downloadRequest.setMimeType("image/jpeg")
//        } else if (jenisFile?.equals(".png", true) == true) {
//            downloadRequest.setMimeType("image/png")
//        } else {
//            downloadRequest.setMimeType("*/*")
//        }
//    }

    private fun callChooseFileFromDevice() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("*/*")
        intent = Intent.createChooser(intent, "Choose from A File")
        resultContract.launch(intent)
    }

    private fun uriPath(mypath: String): String {
        var path = ""
        if (mypath.contains("document/raw:")) {
            path = mypath.replace("/document/raw:", "");
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