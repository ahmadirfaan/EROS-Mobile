package com.finalproject.presentations.employee.claim.pregnant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalproject.R
import com.finalproject.databinding.FragmentClaimInsuranceBinding
import com.finalproject.databinding.FragmentClaimPregnantBinding

class ClaimPregnantFragment : Fragment() {

    private lateinit var binding : FragmentClaimPregnantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentClaimPregnantBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = ClaimPregnantFragment()
    }
}