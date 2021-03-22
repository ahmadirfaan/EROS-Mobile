package com.finalproject.presentations.employee.claim.glasses

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toFile
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.data.models.reimburse.AddReimbursementRequest
import com.finalproject.databinding.FragmentClaimGlassesBinding
import com.finalproject.presentations.employee.claim.ClaimViewModel
import com.finalproject.utils.AppConstant
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ClaimGlassesFragment : Fragment() {

    private lateinit var binding: FragmentClaimGlassesBinding
    private lateinit var loadingDialog: AlertDialog
    private var dataUri: Uri? = null
    private var file : File? = null
    private var uriString: String? = null
    private lateinit var viewModel: ClaimViewModel
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
        binding = FragmentClaimGlassesBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog.build(requireContext())
        initViewModel()
        subscribe()
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
                val bundle = bundleOf("Document" to uriString)
                findNavController().navigate(R.id.action_claimGlassesFragment_to_openPdfFragment, bundle)
            }
            btnSubmitFormGlasses.setOnClickListener {
                val tvNameFile = tvNameFile.text.toString()
                val inputClaimString = inputClaim.text.toString()
                if (tvNameFile.isBlank() || tvNameFile.equals("Tidak Diketahui Filenya")) {
                    Toast.makeText(requireContext(), "File Tidak Benar", Toast.LENGTH_SHORT).show()
                } else if (inputClaimString.isBlank()) {
                    Toast.makeText(requireContext(), "Input Jumlah Claim Tidak Benar", Toast.LENGTH_SHORT).show()
                } else {
                    val requestReimburse = AddReimbursementRequest(
                        endDate = null,
                        employeeId = getEmployeeId(),
                        startDate = null,
                        categoryId = "1",
                        claimFee = inputClaimString.toInt()
                    )
                    viewModel.addReimbursement(requestReimburse)
                }
            }
        }
    }

    private fun callChooseFileFromDevice() {
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("application/pdf")
        intent = Intent.createChooser(intent, "Choose from A File")
        resultContract.launch(intent)
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

    private fun getEmployeeId(): String? {
        return sharedPreferences.getString(AppConstant.APP_ID_EMPLOYEE, "ID Employee")
    }

    private fun uriPath(mypath : String) : String {
        var path = ""
        if(mypath.contains("document/raw:")){
            path = mypath.replace("/document/raw:","");
        }
        return path
    }


    companion object {

        fun newInstance() = ClaimGlassesFragment()
    }
}