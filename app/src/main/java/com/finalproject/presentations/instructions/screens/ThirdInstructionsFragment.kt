package com.finalproject.presentations.instructions.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentThirdInstructionsBinding


class ThirdInstructionsFragment : Fragment() {

    private lateinit var binding : FragmentThirdInstructionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentThirdInstructionsBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.finishBoarding.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    companion object {
        fun newInstance() = ThirdInstructionsFragment()
    }
}