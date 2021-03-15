package com.finalproject.presentations.employee.history.onprogress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentHistoryOnprogressBinding

class HistoryOnProgressFragment : Fragment() {

    private lateinit var binding: FragmentHistoryOnprogressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryOnprogressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_historyOnProgressFragment_to_homeEmployeeFragment2)
        }
        binding.tvMoveFragmentHistorySuccess.setOnClickListener {
            findNavController().navigate(R.id.action_historyOnProgressFragment_to_historySuccessFragment2)
        }
        binding.tvNotAvalaible.visibility = View.GONE
        binding.rvOnProgressHistory.visibility = View.VISIBLE
    }

    companion object {

        @JvmStatic
        fun newInstance() = HistoryOnProgressFragment()
    }
}