package com.finalproject.presentations.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.R
import com.finalproject.databinding.FragmentInstructionsPagerBinding
import com.finalproject.presentations.instructions.screens.FirstInstructionsFragment
import com.finalproject.presentations.instructions.screens.SecondInstructionsFragment
import com.finalproject.presentations.instructions.screens.ThirdInstructionsFragment
import com.finalproject.presentations.introduce.ViewPagerAdapter
import com.finalproject.presentations.introduce.screens.FirstScreenFragment
import com.finalproject.presentations.introduce.screens.SecondScreenFragment
import com.finalproject.presentations.introduce.screens.ThirdScreenFragment

class InstructionsPagerFragment : Fragment() {

    private lateinit var binding : FragmentInstructionsPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentInstructionsPagerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentList = arrayListOf<Fragment>(
            FirstInstructionsFragment.newInstance(),
            SecondInstructionsFragment.newInstance(),
            ThirdInstructionsFragment.newInstance()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = InstructionsPagerFragment()

    }
}