package com.finalproject.presentations.employee.claim.glasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.R
import com.finalproject.databinding.FragmentClaimGlassesBinding

class ClaimGlassesFragment : Fragment() {

    private lateinit var binding : FragmentClaimGlassesBinding

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

    companion object {

        fun newInstance() = ClaimGlassesFragment()
    }
}