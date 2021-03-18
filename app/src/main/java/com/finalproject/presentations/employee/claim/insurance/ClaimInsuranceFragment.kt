package com.finalproject.presentations.employee.claim.insurance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalproject.R
import com.finalproject.databinding.FragmentClaimGlassesBinding
import com.finalproject.databinding.FragmentClaimInsuranceBinding


class ClaimInsuranceFragment : Fragment() {

    private lateinit var binding : FragmentClaimInsuranceBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentClaimInsuranceBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = ClaimInsuranceFragment()

    }
}