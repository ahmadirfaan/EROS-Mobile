package com.finalproject.presentations.employee.claim.spd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalproject.R
import com.finalproject.databinding.FragmentClaimSpdBinding
import com.finalproject.utils.DateUtils


class ClaimSPDFragment : Fragment() {

    private lateinit var binding : FragmentClaimSpdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentClaimSpdBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.apply {
            dateIconStart.setOnClickListener {
                DateUtils.show(requireContext()) {
                    etStartDate.setText(it)
                }
            }
            dateIconEnd.setOnClickListener {
                DateUtils.show(requireContext()) {
                    etEndDate.setText(it)
                }
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ClaimSPDFragment()

    }
}