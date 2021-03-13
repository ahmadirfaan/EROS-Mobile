package com.finalproject.presentations.employee.home

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalproject.databinding.FragmentHomeEmployeeBinding

class HomeEmployeeFragment : Fragment() {

    private lateinit var binding : FragmentHomeEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeEmployeeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.layoutClaimGlasses.setBackgroundColor(Color.parseColor("#FF0000"))
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeEmployeeFragment()
    }
}