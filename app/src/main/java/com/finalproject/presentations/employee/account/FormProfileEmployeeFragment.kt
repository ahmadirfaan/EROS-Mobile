package com.finalproject.presentations.employee.account

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentConfirmAccountBinding
import com.finalproject.databinding.FragmentFormProfileEmployeeBinding
import com.finalproject.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FormProfileEmployeeFragment : Fragment() {

    private lateinit var binding : FragmentFormProfileEmployeeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormProfileEmployeeBinding.inflate(layoutInflater)
        requireActivity().onBackPressedDispatcher.addCallback(this){
                requireActivity().finish()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmitForm.setOnClickListener {
            findNavController().navigate(R.id.action_formProfileEmployeeFragment_to_confirmAccountFragment)
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = FormProfileEmployeeFragment()
    }
}