package com.finalproject.presentations.employee.claim.insurance

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.data.models.reimburse.AddReimbursementRequest
import com.finalproject.databinding.FragmentClaimGlassesBinding
import com.finalproject.databinding.FragmentClaimInsuranceBinding
import com.finalproject.presentations.employee.claim.ClaimViewModel
import com.finalproject.utils.AppConstant
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ClaimInsuranceFragment : Fragment() {

    private lateinit var binding : FragmentClaimInsuranceBinding
    private lateinit var viewModel: ClaimViewModel
    private lateinit var loadingDialog: AlertDialog
    private var dataUri: Uri? = null
    private var file : File? = null
    private var uriString: String? = null
    val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it?.resultCode == Activity.RESULT_OK) {
            dataUri = it.data?.data
            file = File(dataUri?.path?.let { it1 -> uriPath(it1) })
            binding.apply {
                tvNameFile.text = "File : ${file?.name}"
                uriString = it.data?.data.toString()
                btnSeeFile.visibility = View.VISIBLE
            }

        } else {
            binding.tvNameFile.text = "Tidak Diketahui Filenya"
        }
    }

    @Inject
    lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentClaimInsuranceBinding.inflate(layoutInflater)
        initViewModel()
        subscribe()
        loadingDialog = LoadingDialog.build(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root

    }

    override fun onPause() {
        super.onPause()
        loadingDialog.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnUploadFiles.setOnClickListener {
                callChooseFileFromDevice()
            }
            btnSeeFile.setOnClickListener {
                if (file?.extension == "pdf") {
                    val bundle = bundleOf("Document" to uriString)
                    findNavController().navigate(R.id.action_claimInsuranceFragment_to_openPdfFragment, bundle)
                } else if (file?.extension == "jpg" || file?.extension == "png" || file?.extension == "jpeg") {
                    val bundle = bundleOf("Image" to uriString)
                    findNavController().navigate(R.id.action_claimInsuranceFragment_to_openPdfFragment, bundle)
                } else {
                    Toast.makeText(requireContext(), "Pilih File Yang Benar", Toast.LENGTH_SHORT).show()
                }
            }
            btnSubmitFormInsurance.setOnClickListener {
                val formatFile = arrayListOf<String>("jpg","pdf","png", "jpeg")
                val fileSizeInMegaByte = file?.length()?.div(1024)?.div(1024)
                Log.d("FiLE SIZE IN MEGABYte", fileSizeInMegaByte!!.toString())
                val tvNameFile = tvNameFile.text.toString()
                val inputClaimString = inputClaim.text.toString()
                if (tvNameFile.isBlank() || tvNameFile.equals("Tidak Diketahui Filenya")) {
                    Toast.makeText(requireContext(), "File Tidak Benar", Toast.LENGTH_SHORT).show()
                } else if (inputClaimString.isBlank()) {
                    Toast.makeText(requireContext(), "Input Jumlah Claim Tidak Benar", Toast.LENGTH_SHORT).show()
                } else if (fileSizeInMegaByte > 5) {
                    Toast.makeText(requireContext(), "Ukuran Maksimal 5MB", Toast.LENGTH_SHORT).show()
                } else if (!formatFile.contains(file?.extension)) {
                    Toast.makeText(requireContext(), "File Yang Anda Masukkan tidak didukung", Toast.LENGTH_SHORT).show()
                } else {
                    val requestReimburse = AddReimbursementRequest(
                        endDate = null,
                        employeeId = getEmployeeId(),
                        startDate = null,
                        categoryId = "5",
                        claimFee = inputClaimString.toInt()
                    )
                    viewModel.addReimbursement(requestReimburse)
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ClaimViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.addReimbursementLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    file?.let { it1 -> viewModel.uploadFile(it1) }

                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.uploadFileLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), "Sukses Menambahkan Data Reimbursement", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun callChooseFileFromDevice() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("*/*")
        intent = Intent.createChooser(intent, "Choose from A File")
        resultContract.launch(intent)
    }

    private fun getEmployeeId(): String? {
        return sharedPreferences.getString(AppConstant.APP_ID_EMPLOYEE, "ID Employee")
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

    companion object {

        @JvmStatic
        fun newInstance() = ClaimInsuranceFragment()

    }
}