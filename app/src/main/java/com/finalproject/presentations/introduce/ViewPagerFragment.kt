package com.finalproject.presentations.introduce

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.R
import com.finalproject.databinding.FragmentViewPagerBinding
import com.finalproject.presentations.introduce.screens.FirstScreenFragment
import com.finalproject.presentations.introduce.screens.SecondScreenFragment
import com.finalproject.presentations.introduce.screens.ThirdScreenFragment

class ViewPagerFragment : Fragment() {

    private lateinit var binding : FragmentViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerBinding.inflate(layoutInflater)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreenFragment.newInstance(),
            SecondScreenFragment.newInstance(),
            ThirdScreenFragment.newInstance()
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

        fun newInstance() = ViewPagerFragment()
    }
}