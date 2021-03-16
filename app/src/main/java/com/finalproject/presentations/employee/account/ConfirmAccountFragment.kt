package com.finalproject.presentations.employee.account

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.finalproject.databinding.FragmentConfirmAccountBinding
import com.finalproject.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ConfirmAccountFragment : Fragment() {


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: FragmentConfirmAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmAccountBinding.inflate(layoutInflater)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
        clearSharedPreferences()
        return binding.root
    }

    private fun clearSharedPreferences() {
        sharedPreferences.edit().remove(AppConstant.ON_LOGIN_FINISHED).apply()
        sharedPreferences.edit().remove(AppConstant.APP_ID_LOGIN).apply()
        sharedPreferences.edit().remove(AppConstant.APP_ID_EMPLOYEE).apply()
    }

    companion object {

        @JvmStatic
        fun newInstance() = ConfirmAccountFragment()
    }
}