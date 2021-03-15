package com.finalproject.presentations.introduce.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentThirdScreenBinding
import com.finalproject.utils.AppConstant


class ThirdScreenFragment : Fragment() {

    private lateinit var binding: FragmentThirdScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdScreenBinding.inflate(layoutInflater)
        binding.finishBoarding.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            onBoardingFinished()
        }
        return binding.root
    }

    private fun onBoardingFinished() {
        val sharedPreference = requireActivity().getSharedPreferences(AppConstant.ON_BOARDING_FINISHED, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    companion object {

        @JvmStatic
        fun newInstance() = ThirdScreenFragment()
    }
}