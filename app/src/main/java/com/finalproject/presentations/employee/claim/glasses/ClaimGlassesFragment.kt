package com.finalproject.presentations.employee.claim.glasses

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.finalproject.databinding.FragmentClaimGlassesBinding

class ClaimGlassesFragment : Fragment() {

    private lateinit var binding: FragmentClaimGlassesBinding

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
            binding.btnUploadFiles.setOnClickListener {
            }
        }
    }


    companion object {

        fun newInstance() = ClaimGlassesFragment()
    }
}