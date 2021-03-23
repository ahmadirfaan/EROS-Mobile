package com.finalproject.presentations.employee.account.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.di.qualifier.RegisterLoginAccountRepoQualifier
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountFragmentViewModel @Inject constructor(
    @RegisterLoginAccountRepoQualifier
    private val registerLoginAccountRepo: RegisterLoginAccountRepository
) : ViewModel() {

    private var _setProfile = MutableLiveData<ResourceState>()
    val setProfile : LiveData<ResourceState>
    get() {
        return _setProfile
    }

    private var _sendBundleData = MutableLiveData<ResourceState>()
    val sendBundleData : LiveData<ResourceState>
        get() {
            return _sendBundleData
        }


    fun fillProfileAccount(idLogin : String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _setProfile.postValue(ResourceState.loading())
                val response = registerLoginAccountRepo.findEmployeeByIdLogin(idLogin)
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    _setProfile.postValue(ResourceState.success(responseBody?.data))
                } else {
                    _setProfile.postValue(ResourceState.failured(response.message()))
                }
            } catch (e : Exception) {
                e.printStackTrace()
                _setProfile.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))

            }
        }
    }

    fun sendBundleDataForEditAccount(idLogin : String) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val response = registerLoginAccountRepo.findEmployeeByIdLogin(idLogin)
                val responseBody = response.body()
                _sendBundleData.postValue(ResourceState.loading())
                if(response.isSuccessful) {
                    val employeeResponse = responseBody?.data
                    employeeResponse?.let {
                        _sendBundleData.postValue(ResourceState.success(it))
                    }
                } else {
                    _sendBundleData.postValue(ResourceState.failured(responseBody?.message as String?))
                }
            } catch (e : Exception) {
                e.printStackTrace()
                _sendBundleData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

}