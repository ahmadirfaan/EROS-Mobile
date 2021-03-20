package com.finalproject.presentations.employee.home

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentHomeEmployeeBinding

class HomeEmployeeFragment : Fragment() {

    private lateinit var binding : FragmentHomeEmployeeBinding
    private var isBackPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeEmployeeBinding.inflate(layoutInflater)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            if(!isBackPressed) {
                Toast.makeText(requireContext(), "Tekan Sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
                isBackPressed = true
            } else {
                requireActivity().finish()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutClaimGlasses.setOnClickListener {
            findNavController().navigate(R.id.action_homeEmployeeFragment_to_claimGlassesFragment)
        }
        binding.layoutClaimInsurance.setOnClickListener {
            findNavController().navigate(R.id.action_homeEmployeeFragment_to_claimInsuranceFragment)
        }
        binding.layoutClaimPregnant.setOnClickListener {
            findNavController().navigate(R.id.action_homeEmployeeFragment_to_claimPregnantFragment)
        }
        binding.layoutClaimTraining.setOnClickListener {
            findNavController().navigate(R.id.action_homeEmployeeFragment_to_claimTrainingFragment)
        }
        binding.layoutClaimTravelDuty.setOnClickListener {
            findNavController().navigate(R.id.action_homeEmployeeFragment_to_claimSPDFragment)
        }
//        binding.layoutClaimGlasses.setBackgroundColor(Color.parseColor("#FF0000")) untuk menghilangkan tombol click listener dan ganti warna pada karyawan on site
    }

    override fun onPause() {
        super.onPause()
        isBackPressed = false
    }

    private fun checkForPermission(permission : String, requestCode : Int) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> {

                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = HomeEmployeeFragment()
    }
}