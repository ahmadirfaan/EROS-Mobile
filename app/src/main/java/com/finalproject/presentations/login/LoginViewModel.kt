package com.finalproject.presentations.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.utils.ResourceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    fun checkEmailPasswordLogin(email : String, password : String) {
        CoroutineScope(Dispatchers.Main).launch {
            _inputValidation.postValue(ResourceState.loading())
            delay(2000)
            val check = ArrayList<Int>()
            if(!email.isBlank() && email.matches(emailPattern.toRegex())) {
                check.add(1)
            }
            if(!password.isBlank() ) {
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
}