package com.finalproject.presentations.employee.account.formprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
class FormProfileEmployeeViewModel @Inject constructor(
    @RegisterLoginAccountRepoQualifier
    private val registerLoginAccountRepo : RegisterLoginAccountRepository
): ViewModel() {

    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    fun inputValidation(vararg input: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _inputValidation.postValue(ResourceState.loading())
            delay(500)
            val check = ArrayList<Int>()
            for (i in input) {
                if (i.isNotEmpty() || i.isNotBlank()) {
                    check.add(1)
                }
            }
            if (check.size == input.size) {
                _inputValidation.postValue(ResourceState.success(true))
            } else {
                _inputValidation.postValue(ResourceState.failured("INPUT MUST NOT EMPTY"))
            }

        }
    }
}