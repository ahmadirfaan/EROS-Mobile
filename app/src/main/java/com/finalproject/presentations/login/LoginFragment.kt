package com.finalproject.presentations.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.finalproject.R
import com.finalproject.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        CoroutineScope(Dispatchers.Main).launch {
//            delay(1000)
//            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeAdminHCFragment)
//        }
        binding.apply {
            btnLogin.setOnClickListener {
                val usernameString = inputUsername.editText?.text.toString()
                val passswordString = inputUsername.editText?.text.toString()
                if(usernameString.equals("admin" )&& passswordString.equals("admin")) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeAdminHCFragment)
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeEmployeeFragment)
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}