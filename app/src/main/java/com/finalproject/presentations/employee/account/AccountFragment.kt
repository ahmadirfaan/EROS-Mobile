package com.finalproject.presentations.employee.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private lateinit var binding : FragmentAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_accountFragment_to_homeEmployeeFragment2)
        }
        binding.btnEditAccount.setOnClickListener {
            findNavController().navigate(R.id.action_accountFragment_to_formProfileEmployeeFragment)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}