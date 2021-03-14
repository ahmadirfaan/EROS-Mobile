package com.finalproject.presentations.employee.history.success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentHistorySuccessBinding

class HistorySuccessFragment : Fragment() {

    private lateinit var binding: FragmentHistorySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistorySuccessBinding.inflate(layoutInflater)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_historySuccessFragment2_to_homeEmployeeFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvMoveFragmentHistoryOnProgress.setOnClickListener {
            findNavController().navigate(R.id.action_historySuccessFragment2_to_historyOnProgressFragment)
        }
    }


    companion object {

        fun newInstance() = HistorySuccessFragment()

    }
}