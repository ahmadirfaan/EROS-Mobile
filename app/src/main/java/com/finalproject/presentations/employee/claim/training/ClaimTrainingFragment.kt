package com.finalproject.presentations.employee.claim.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalproject.R
import com.finalproject.databinding.FragmentClaimSpdBinding
import com.finalproject.databinding.FragmentClaimTrainingBinding
import com.finalproject.utils.DateUtils

class ClaimTrainingFragment : Fragment() {

    private lateinit var binding : FragmentClaimTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentClaimTrainingBinding.inflate(layoutInflater)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

        @JvmStatic
        fun newInstance() = ClaimTrainingFragment()
    }
}