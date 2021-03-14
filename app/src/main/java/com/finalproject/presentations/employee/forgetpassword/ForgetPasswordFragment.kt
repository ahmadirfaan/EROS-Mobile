package com.finalproject.presentations.employee.forgetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.finalproject.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = ForgetPasswordFragment()

    }
}