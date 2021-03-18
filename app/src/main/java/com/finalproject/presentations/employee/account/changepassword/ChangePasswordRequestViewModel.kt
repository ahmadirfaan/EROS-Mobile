package com.finalproject.presentations.employee.account.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.account.ChangePasswordRequest
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.di.qualifier.RegisterLoginAccountRepoQualifier
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordRequestViewModel @Inject constructor(
    @RegisterLoginAccountRepoQualifier
    private val registerLoginAccountRepo: RegisterLoginAccountRepository
) : ViewModel() {

    private var _changePasswordLiveData = MutableLiveData<ResourceState>()
    val changePasswordLiveData : LiveData<ResourceState>
    get() {
        return _changePasswordLiveData
    }

    fun changePassword(request : ChangePasswordRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _changePasswordLiveData.postValue(ResourceState.loading())
                val response = registerLoginAccountRepo.changePasswordByIdLogin(request)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody?.code != 200) {
                        _changePasswordLiveData.postValue(ResourceState.failured(responseBody?.message))
                    } else {
                        _changePasswordLiveData.postValue(ResourceState.success(responseBody?.data))
                    }
                } else {
                    _changePasswordLiveData.postValue(ResourceState.failured("There is Something Wrong with Server"))
                }
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}