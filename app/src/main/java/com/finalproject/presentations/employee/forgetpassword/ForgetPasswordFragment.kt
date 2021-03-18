package com.finalproject.presentations.employee.forgetpassword

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
import com.finalproject.data.models.account.ForgotPasswordRequest
import com.finalproject.databinding.FragmentForgetPasswordBinding
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var emailTextWatcher : TextWatcher
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var loadingDialog : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
        loadingDialog = LoadingDialog.build(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateEmailRunTime()
        binding.apply {
            btnResetPassword.setOnClickListener {
                val emailString = signUpInputEmail.editText?.text.toString()
                if(emailString.matches(emailPattern.toRegex())) {
                    val request = ForgotPasswordRequest(email = emailString, status = "Reset Password")
                    viewModel.resetPassword(request)
                } else {

                }
            }
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
    }

    private fun validateEmailRunTime() {
        emailTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(!s.toString().matches(emailPattern.toRegex())) {
                    binding.signUpInputEmail.editText?.error="Please enter a valid email address"
                }
            }
        }
        binding.signUpInputEmail.editText?.addTextChangedListener(emailTextWatcher)
    }

    private fun subscribe() {
        viewModel.resetPassword.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> {
                    loadingDialog.show()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    binding.apply {
                        Toast.makeText(
                            requireContext(),
                            "Please Check You Email for The Updated Password Dont Forget to Change Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    clearInput()
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    clearInput()
                }
            }
        })
    }

    private fun clearInput() {
        binding.apply {
            signUpInputEmail.editText?.setText("")
            signUpInputEmail.editText?.error = null
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetPassword.removeObservers(this)
        loadingDialog.cancel()
    }

    companion object {

        @JvmStatic
        fun newInstance() = ForgetPasswordFragment()

    }
}