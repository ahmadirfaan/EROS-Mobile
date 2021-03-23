package com.finalproject.presentations.employee.account.formprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.account.FormAccountRequest
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
    private val registerLoginAccountRepo: RegisterLoginAccountRepository
) : ViewModel() {

    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    private var _formLiveData = MutableLiveData<ResourceState>()
    val formLiveData: LiveData<ResourceState>
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

    fun fillEditFormProfileForFirstTime(idEmployee : String, request : FormAccountRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _inputValidation.postValue(ResourceState.loading())
                val response = registerLoginAccountRepo.editFormEmployeeProfile(idEmployee = idEmployee, request = request)
                val responseBody = response.body()
                Log.d("Response Code", "Response Code : ${response.code()}")
                if(response.isSuccessful) {
                    Log.d("Response Code", "Response Body Code : ${responseBody?.code}")
                    Log.d("Response Code", "Response Body MEssagea : ${responseBody?.message}")
                    if(responseBody?.code?.equals(200) == true) {
                        _formLiveData.postValue(ResourceState.success(responseBody))
                    } else {
                        Log.d("MASUK EDIT", "MASUKKK DONGG PLISSS")
                        _formLiveData.postValue(ResourceState.failured(responseBody?.message.toString()))
                    }
                } else {
                    _formLiveData.postValue(ResourceState.failured(response.message()))
                }
            } catch (e : Exception) {
                e.printStackTrace()
                _inputValidation.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }
}