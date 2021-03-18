package com.finalproject.presentations.instructions.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.finalproject.R
import com.finalproject.databinding.FragmentSecondInstructionsBinding


class SecondInstructionsFragment : Fragment() {

    private lateinit var binding : FragmentSecondInstructionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSecondInstructionsBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        binding.nextSecondBoarding.setOnClickListener {
            viewPager?.currentItem = 2
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = SecondInstructionsFragment()
    }
}