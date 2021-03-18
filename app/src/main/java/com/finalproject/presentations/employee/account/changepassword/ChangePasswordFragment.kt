package com.finalproject.presentations.employee.account.changepassword

import android.content.SharedPreferences
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
import com.finalproject.data.models.account.ChangePasswordRequest
import com.finalproject.databinding.FragmentChangePasswordBinding
import com.finalproject.utils.AppConstant
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var viewModel: ChangePasswordRequestViewModel
    private lateinit var loadingDialog: AlertDialog
    private lateinit var confirmPasswordTextWatcher : TextWatcher

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog.build(requireContext())
        initViewModel()
        subscribe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateDetailsOnRuntime()
        binding.apply {
            btnChangePassword.setOnClickListener {
                val inputPassword = changeInputPassword.editText?.text.toString()
                if (inputPassword.length < 8) {
                    Toast.makeText(requireContext(), "Password Length Must Be Min 8 length", Toast.LENGTH_SHORT).show()
                } else {
                    val request = ChangePasswordRequest(password = inputPassword, idLogin = getIdLogin() )
                    viewModel.changePassword(request)
                }
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ChangePasswordRequestViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.changePasswordLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.FAILURE -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    loadingDialog.hide()
                }
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), "Success Change Password", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun validateDetailsOnRuntime() {

        confirmPasswordTextWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.changeConfirmPassword.isPasswordVisibilityToggleEnabled = true

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.changeConfirmPassword.isPasswordVisibilityToggleEnabled = true

            }

            override fun afterTextChanged(s: Editable?) {
                if(!binding.changeInputPassword.editText?.text.toString().contentEquals(s.toString())) {
                    val editTextConfirmPass = binding.changeConfirmPassword.editText
                    editTextConfirmPass?.error = "Password do not match"
                    if(editTextConfirmPass?.error != null) {
                        binding.changeConfirmPassword.isPasswordVisibilityToggleEnabled = false
                    }
                } else {
                    binding.changeConfirmPassword.isPasswordVisibilityToggleEnabled = true
                }
            }

        }
        binding.changeConfirmPassword.editText?.addTextChangedListener(confirmPasswordTextWatcher)
    }

    private fun getIdLogin() : String? {
        return sharedPreferences.getString(AppConstant.APP_ID_LOGIN, "ID Login")
    }

    override fun onPause() {
        super.onPause()
        loadingDialog.cancel()
        viewModel.changePasswordLiveData.removeObservers(this)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ChangePasswordFragment()
    }
}