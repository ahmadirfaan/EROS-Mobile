package com.finalproject.presentations.employee.claim.glasses

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentClaimGlassesBinding

class ClaimGlassesFragment : Fragment() {

    private lateinit var binding: FragmentClaimGlassesBinding
    private var uriString : String? = null
    val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it?.resultCode == Activity.RESULT_OK) {
            binding.apply {
                tvNameFile.text = "File :\n${it.data?.data?.pathSegments?.last()}"
                uriString = it.data?.data.toString()
                btnSeeFile.visibility = View.VISIBLE
            }
        } else {
            binding.tvNameFile.text = "Tidak Diketahui Filenya"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentClaimGlassesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
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
        }
    }

    private fun callChooseFileFromDevice() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("application/pdf")
        resultContract.launch(intent)
    }




    companion object {

        fun newInstance() = ClaimGlassesFragment()
    }
}