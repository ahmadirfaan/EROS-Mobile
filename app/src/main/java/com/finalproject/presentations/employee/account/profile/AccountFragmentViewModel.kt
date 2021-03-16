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


    fun fillProfileAccount(idLogin : String) {
        CoroutineScope(Dispatchers.Main).launch {
            _setProfile.postValue(ResourceState.loading())
            val response = registerLoginAccountRepo.findEmployeeByIdLogin(idLogin)
            if(response.isSuccessful) {
                val responseBody = response.body()
                _setProfile.postValue(ResourceState.success(responseBody?.data))
            } else {
                _setProfile.postValue(ResourceState.failured(response.message()))
            }
        }
    }

}