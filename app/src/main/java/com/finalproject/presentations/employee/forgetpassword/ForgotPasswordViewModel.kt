package com.finalproject.presentations.employee.forgetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.account.ForgotPasswordRequest
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.di.qualifier.RegisterLoginAccountRepoQualifier
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    @RegisterLoginAccountRepoQualifier
    private val registerLoginAccountRepo: RegisterLoginAccountRepository
) : ViewModel() {

    private var _resetPassword = MutableLiveData<ResourceState>()
    val resetPassword: LiveData<ResourceState>
        get() {
            return _resetPassword
        }


    fun resetPassword(request : ForgotPasswordRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _resetPassword.postValue(ResourceState.loading())
                val response = registerLoginAccountRepo.forgotPassword(request)
                val responseBody = response.body()
                if(response.isSuccessful) {
                    if(responseBody?.code != 200) {
                        _resetPassword.postValue(ResourceState.failured(responseBody?.message))
                    } else if(responseBody?.data?.status.equals("Failed")) {
                        _resetPassword.postValue(ResourceState.failured("Email yang Anda Masukkan tidak terdaftar di Databases Kami"))
                    }
                    else {
                        _resetPassword.postValue(ResourceState.success(responseBody))
                    }
                } else {
                    _resetPassword.postValue(ResourceState.failured(response.message()))
                }
            } catch (e : Exception) {
                e.printStackTrace()
                _resetPassword.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }
        }
    }

}