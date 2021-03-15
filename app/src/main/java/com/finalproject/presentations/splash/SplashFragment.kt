package com.finalproject.presentations.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            if(onBoardingFinished() && onLoginFinished()) {
                findNavController().navigate(R.id.action_splashFragment_to_homeEmployeeFragment)
            }
            else if(onBoardingFinished()) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }
    }

    private fun onBoardingFinished() : Boolean {
        val sharedPreference = requireActivity().getSharedPreferences(AppConstant.ON_BOARDING_FINISHED, Context.MODE_PRIVATE)
        return sharedPreference.getBoolean("Finished", false)
    }

    private fun onLoginFinished() : Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences(AppConstant.ON_LOGIN_FINISHED, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Login", false)
    }

    companion object {

        fun newInstance() = SplashFragment()

    }
}