package com.finalproject.presentations.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.repositories.registeraccount.RegisterAccountRepository
import com.finalproject.di.qualifier.RegisterAccountRepoQualifier
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(@RegisterAccountRepoQualifier private val registerAccountRepo: RegisterAccountRepository) : ViewModel() {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    private var _createAccountLiveData = MutableLiveData<ResourceState>()
    val createAccountLiveData: LiveData<ResourceState>
        get() {
            return _createAccountLiveData
        }

    fun checkInputEmailPassword(email: String, password: String, confirmPassword: String) {
        CoroutineScope(Dispatchers.Main).launch {
            _inputValidation.postValue(ResourceState.loading())
            delay(2000)
            val check = ArrayList<Int>()
            if (!email.isBlank() && email.matches(emailPattern.toRegex())) {
                check.add(1)
            }
            if (!password.isBlank() && password.contentEquals(confirmPassword)) {
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

    fun registerAccount(request: RegisterAccountRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            _createAccountLiveData.postValue(ResourceState.loading())
            val response = registerAccountRepo.createAccountEmployee(request)
            if (response.isSuccessful) {
                val responseBody = response.body()
                _createAccountLiveData.postValue(ResourceState.success(responseBody?.data))
            } else {
                _createAccountLiveData.postValue(ResourceState.failured("Ada yang salah dalam memproses akun"))
            }
        }
    }
}