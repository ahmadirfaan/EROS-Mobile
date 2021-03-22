package com.finalproject.presentations.login

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.finalproject.R
import com.finalproject.data.models.account.LoginResponse
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.databinding.FragmentLoginBinding
import com.finalproject.utils.AppConstant
import com.finalproject.utils.LoadingDialog
import com.finalproject.utils.ResourceStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var loadingDialog: AlertDialog
    private var isBackPressed = false
    private lateinit var emailTextWatcher: TextWatcher

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        subscribe()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (!isBackPressed) {
                Toast.makeText(requireContext(), "Tekan Sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
                isBackPressed = true
            } else {
                requireActivity().finish()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
//        CoroutineScope(Dispatchers.Main).launch {
//            delay(1000)
//            findNavController().navigate(R.id.action_loginFragment_to_homeEmployeeFragment)
//        }

        loadingDialog = LoadingDialog.build(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateEmailOnRuntime()
        binding.apply {
            btnLogin.setOnClickListener {
                val usernameString = inputEmailLogin.editText?.text.toString()
                val passswordString = inputPasswordLogin.editText?.text.toString()
                if(usernameString.matches(emailPattern.toRegex())) {
                    viewModel.checkEmailPasswordLogin(email = usernameString, password = passswordString)
                } else {
                    Toast.makeText(requireContext(), "Harap Masukkan Email yang Benar", Toast.LENGTH_SHORT).show()
                }
            }
            btnSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
            btnForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_forgetPasswordFragment)
            }
            btnInstructions.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_instructionsPagerFragment)
            }
        }
    }

//    Untuk mengetes onBoarding Finished
//    private fun clearSharedPreferencesFinishBoarding() {
//        val sharedPreference = requireActivity().getSharedPreferences(AppConstant.ON_BOARDING_FINISHED, Context.MODE_PRIVATE)
//        sharedPreference.edit().clear().commit()
//    }

    //Untuk mengecek apakah input email yang dijalankan sudah memenuhi kriteria inputan sebuah email
    private fun validateEmailOnRuntime() {
        emailTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.toString().matches(emailPattern.toRegex())) {
                    binding.inputEmailLogin.editText?.error = "Please enter a valid email address"
                }
            }
        }
        binding.inputEmailLogin.editText?.addTextChangedListener(emailTextWatcher)
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
                        val emailString = inputEmailLogin.editText?.text.toString()
                        val passswordString = inputPasswordLogin.editText?.text.toString()
                        val loginAccount = RegisterAccountRequest(email = emailString, password = passswordString)
                        viewModel.loginAccountToHome(loginAccount)
//                        if (usernameString.equals("admin@admin.com") && passswordString.equals("admin")) {
//                            findNavController().navigate(R.id.action_loginFragment_to_homeAdminHCFragment)
//                        } else {
//                            findNavController().navigate(R.id.action_loginFragment_to_homeEmployeeFragment)
//                        }
                    }
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.loginAccount.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val data = it.data as LoginResponse
                    sharedPreferences.edit().putString(AppConstant.APP_ID_LOGIN, data.data?.id).apply()
                    sharedPreferences.getString(AppConstant.APP_ID_LOGIN, "Login ID")?.let { it1 ->
                        viewModel.checkFormLiveData(it1)
                    }
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.checkFormLiveData.observe(this, {
            when (it.status) {
                ResourceStatus.LOADING -> loadingDialog.show()
                ResourceStatus.SUCCESS -> {
                    loadingDialog.hide()
                    val successCode = it?.data as Int
                    when (successCode) {
                        1 -> {
                            findNavController().navigate(R.id.action_loginFragment_to_homeEmployeeFragment)
                            sharedPreferences.edit().putBoolean(AppConstant.ON_LOGIN_FINISHED, true).apply()
                        }
                        2 -> findNavController().navigate(R.id.action_loginFragment_to_confirmAccountFragment)
                        3 -> findNavController().navigate(R.id.action_loginFragment_to_formProfileEmployeeFragment)
                    }
                }
                ResourceStatus.FAILURE -> {
                    loadingDialog.hide()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    override fun onPause() {
        super.onPause()
        Log.d("ON PAUSE LOGIN FRAGMENT", "ON PAUSE LOGIN FRAGMENT")
        clearText()
    }

    override fun onResume() {
        super.onResume()
        Log.d("ON RESUME LOGIN FRAGMENT", "ON RESUME LOGIN FRAGMENT")
        binding.etUsername.error = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ON DESTROY LOGIN FRAGMENT", "ON DESTROY LOGIN FRAGMENT")
    }

    private fun clearText() {
        binding.inputEmailLogin.editText?.text = null
        binding.inputPasswordLogin.editText?.text = null
        loadingDialog.cancel()
    }

    private fun clearObservers() {
        viewModel.inputValidation.removeObservers(this)
        viewModel.loginAccount.removeObservers(this)
        viewModel.checkFormLiveData.removeObservers(this)
    }


    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}