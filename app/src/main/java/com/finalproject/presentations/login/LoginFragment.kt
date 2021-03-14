package com.finalproject.presentations.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.databinding.FragmentLoginBinding
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var loadingDialog : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
//        CoroutineScope(Dispatchers.Main).launch {
//            delay(1000)
//            findNavController().navigate(R.id.action_loginFragment_to_homeAdminHCFragment)
//        }
        initViewModel()
        subscribe()
        loadingDialog = LoadingDialog.build(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                val usernameString = inputEmailLogin.editText?.text.toString()
                val passswordString = inputPasswordLogin.editText?.text.toString()
                viewModel.checkEmailPasswordLogin(email = usernameString, password = passswordString)
            }
            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
            btnForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
            }
//            btnForgotPassword.setOnClickListener {
//                clearSharedPreferencesFinishBoarding()
//            }
        }
        validateEmailOnRuntime()
    }

//    Untuk mengetes onBoarding Finished
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
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    binding.apply {
                        val usernameString = inputEmailLogin.editText?.text.toString()
                        val passswordString = inputPasswordLogin.editText?.text.toString()
                        if (usernameString.equals("admin@admin.com") && passswordString.equals("admin")) {
                            findNavController().navigate(R.id.action_loginFragment_to_homeAdminHCFragment)
                        } else {
                            findNavController().navigate(R.id.action_loginFragment_to_homeEmployeeFragment)
                        }
                    }

                    Toast.makeText(requireContext(), "Success Login", Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
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