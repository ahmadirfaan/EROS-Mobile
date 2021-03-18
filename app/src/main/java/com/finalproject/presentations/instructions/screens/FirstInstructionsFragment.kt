package com.finalproject.presentations.instructions.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.finalproject.R
import com.finalproject.databinding.FragmentFirstInstructionsBinding


class FirstInstructionsFragment : Fragment() {

    private lateinit var binding : FragmentFirstInstructionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFirstInstructionsBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        binding.nextFirstBoarding.setOnClickListener {
            viewPager?.currentItem = 1
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = FirstInstructionsFragment()

    }
}