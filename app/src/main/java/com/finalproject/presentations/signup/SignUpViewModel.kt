package com.finalproject.presentations.signup

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
class SignUpViewModel @Inject constructor(@RegisterLoginAccountRepoQualifier private val registerAccountRepo: RegisterLoginAccountRepository) :
    ViewModel() {

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
            try {
                _inputValidation.postValue(ResourceState.loading())
                delay(2000)
                val check = ArrayList<Int>()
                if (!email.isBlank() && email.matches(emailPattern.toRegex())) {
                    check.add(5)
                }
                if (!password.isBlank() && password.contentEquals(confirmPassword)) {
                    check.add(0)
                }
                Log.d("ARRAY LIST SIGN UP EMAIL", check.toString())
                if (check.size == 2) {
                    _inputValidation.postValue(ResourceState.success(true))
                } else if(check.size == 0) {
                    _inputValidation.postValue(ResourceState.failured("Format Email Salah serta Password dan Confirm Password Tidak Sama"))
                }else {
                    if(check[0] == 5) {
                        _inputValidation.postValue(ResourceState.failured("Password dan Confirm Password Harus Sama"))
                    } else if(check[0] == 0) {
                        _inputValidation.postValue(ResourceState.failured("Masukkan Email dengan Benar"))
                    }
                }
            } catch (e : Exception) {
                e.printStackTrace()
                _inputValidation.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

    fun registerAccount(request: RegisterAccountRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _createAccountLiveData.postValue(ResourceState.loading())
                val response = registerAccountRepo.createAccountEmployee(request)
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if(responseBody?.code?.equals(200) == true) {
                        _createAccountLiveData.postValue(ResourceState.success(responseBody?.data))
                    } else {
                        _createAccountLiveData.postValue(ResourceState.failured("Email sudah digunakan"))
                    }
                } else {
                    _createAccountLiveData.postValue(ResourceState.failured(response.message()))
                }
            } catch (e : Exception) {
                e.printStackTrace()
                _createAccountLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }
        }
    }
}