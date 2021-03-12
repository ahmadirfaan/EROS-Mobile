package com.finalproject.presentations.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentLoginBinding
import com.finalproject.utils.ResourceStatus


class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        initViewModel()
        subscribe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                val usernameString = inputEmailLogin.editText?.text.toString()
                val passswordString = inputPasswordLogin.editText?.text.toString()
                viewModel.checkEmailPasswordLogin(email = usernameString, password = passswordString)
                if (usernameString.equals("admin@admin.com") && passswordString.equals("admin")) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeAdminHCFragment)
                } else {
                    findNavController().navigate(R.id.action_loginFragment_to_homeEmployeeFragment)
                }
            }
            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
//            btnForgotPassword.setOnClickListener {
//                clearSharedPreferencesFinishBoarding()
//            }
        }
        validateEmailOnRuntime()
    }

//    //Untuk mengetes onBoarding Finished
//    private fun clearSharedPreferencesFinishBoarding() {
//        val sharedPreference = requireActivity().getSharedPreferences("OnBoardingFinished", Context.MODE_PRIVATE)
//        sharedPreference.edit().clear().commit()
//    }

    //Untuk mengecek apakah input email yang dijalankan sudah memenuhi kriteria inputan sebuah email
    private fun validateEmailOnRuntime() {
        binding.inputEmailLogin.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().matches(emailPattern.toRegex())) {
                    binding.inputEmailLogin.editText?.error = "Please enter a valid email address"
                }
            }
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.inputValidation.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success Create Account", Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}