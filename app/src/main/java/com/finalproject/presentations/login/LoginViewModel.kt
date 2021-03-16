package com.finalproject.presentations.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.di.qualifier.RegisterLoginAccountRepoQualifier
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @RegisterLoginAccountRepoQualifier
    private val registerLoginAccountRepo : RegisterLoginAccountRepository
    ): ViewModel() {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    private var _loginAccount = MutableLiveData<ResourceState>()
    val loginAccount: LiveData<ResourceState>
        get() {
            return _loginAccount
        }

    fun checkEmailPasswordLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            _inputValidation.postValue(ResourceState.loading())
            delay(2000)
            val check = ArrayList<Int>()
            if (!email.isBlank() && email.matches(emailPattern.toRegex())) {
                check.add(1)
            }
            if (!password.isBlank()) {
                check.add(1)
            }
            Log.d("ARRAY LIST SIGN UP EMAIL", check.toString())
            if (check.size == 2) {
                _inputValidation.postValue(ResourceState.success(true))
            } else {
                _inputValidation.postValue(ResourceState.failured("Something Wrong"))
            }
        }
    }

    fun loginAccountToHome(request : RegisterAccountRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            _loginAccount.postValue(ResourceState.loading())
            val response = registerLoginAccountRepo.loginAccountEmployee(request)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if(responseBody?.data == null) {
                    _loginAccount.postValue(ResourceState.failured(responseBody?.message))
                } else if(responseBody?.data?.role?.id != 4) {
                    _loginAccount.postValue(ResourceState.failured("Admin Tidak Diperbolehkan Disini"))
                } else {
                    _loginAccount.postValue(ResourceState.success(responseBody))
                }
            } else {
                _loginAccount.postValue(ResourceState.failured(response.message()))
            }
        }
    }
}