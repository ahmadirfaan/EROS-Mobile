package com.finalproject.presentations.introduce.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.finalproject.R
import com.finalproject.databinding.FragmentFirstScreenBinding

class FirstScreenFragment : Fragment() {

    private lateinit var binding : FragmentFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstScreenBinding.inflate(layoutInflater)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        binding.nextFirstBoarding.setOnClickListener {
            viewPager?.currentItem = 1
        }
        return binding.root
    }

    companion object {
        fun newInstance() = FirstScreenFragment()
    }
}