package com.finalproject.presentations.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.databinding.FragmentSignUpBinding
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        initViewModel()
        subscribe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateDetailsOnRuntime()
        binding.apply {
            btnCreateAccount.setOnClickListener {
                val passwordString = signUpInputPassword.editText?.text.toString()
                val confirmPasswordString = signUpConfirmPassword.editText?.text.toString()
                val emailString = signUpInputEmail.editText?.text.toString()
                viewModel.checkInputEmailPassword(password = passwordString, email = emailString, confirmPassword = confirmPasswordString)
            }
        }
    }

    private fun validateDetailsOnRuntime() {
        binding.signUpInputEmail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(!s.toString().matches(emailPattern.toRegex())) {
                    binding.signUpInputEmail.editText?.error="Please enter a valid email address"
                }
            }
        })
        binding.signUpConfirmPassword.editText?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(!binding.signUpInputPassword.editText?.text.toString().contentEquals(s.toString())) {
                    binding.signUpConfirmPassword.editText?.error = "Password do not match"
                }
            }

        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.inputValidation.observe(this, {
            when(it.status) {
                ResourceStatus.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                ResourceStatus.SUCCESS -> {
                    val emailString = binding.signUpInputEmail.editText?.text.toString()
                    val passwordString = binding.signUpInputPassword.editText?.text.toString()
                    val registerAccount = RegisterAccountRequest(email = emailString, password = passwordString)
                    viewModel.registerAccount(request = registerAccount)
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
        fun newInstance() = SignUpFragment()
    }

}