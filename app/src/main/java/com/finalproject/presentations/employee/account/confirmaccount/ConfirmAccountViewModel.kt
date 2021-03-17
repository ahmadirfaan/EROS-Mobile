package com.finalproject.presentations.employee.account.confirmaccount

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.di.qualifier.RegisterLoginAccountRepoQualifier
import com.finalproject.utils.AppConstant
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmAccountViewModel @Inject constructor(
    @RegisterLoginAccountRepoQualifier
    private val registerLoginAccountRepo : RegisterLoginAccountRepository
): ViewModel() {

    private var _sendBundleData = MutableLiveData<ResourceState>()
    val sendBundleData : LiveData<ResourceState>
        get() {
            return _sendBundleData
        }

    fun sendBundleDataForEditAccount(idLogin : String) {
        CoroutineScope(Dispatchers.Default).launch {
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
        }
    }
}