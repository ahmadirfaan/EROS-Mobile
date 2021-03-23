 package com.finalproject.presentations.login

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.di.qualifier.RegisterLoginAccountRepoQualifier
import com.finalproject.utils.AppConstant
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @RegisterLoginAccountRepoQualifier
    private val registerLoginAccountRepo: RegisterLoginAccountRepository
) : ViewModel() {


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var _inputValidation = MutableLiveData<ResourceState>()
    val inputValidation: LiveData<ResourceState>
        get() {
            return _inputValidation
        }

    private var _loginAccount = MutableLiveData<ResourceState>()
    val loginAccount: LiveData<ResourceState>
        get() {
            return _loginAccount
        }

    private var _checkFormLiveData = MutableLiveData<ResourceState>()
    val checkFormLiveData: LiveData<ResourceState>
        get() {
            return _checkFormLiveData
        }

    fun checkEmailPasswordLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _inputValidation.postValue(ResourceState.loading())
                delay(2000)
                val check = ArrayList<Int>()
                if (!email.isBlank() && email.matches(emailPattern.toRegex())) {
                    check.add(1)
                }
                if (!password.isBlank()) {
                    check.add(1)
                }
                Log.d("ARRAY LIST SIGN UP EMAIL", check.toString())
                if (check.size == 2) {
                    _inputValidation.postValue(ResourceState.success(true))
                } else {
                    _inputValidation.postValue(ResourceState.failured("Something Wrong"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _inputValidation.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

    fun loginAccountToHome(request: RegisterAccountRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _loginAccount.postValue(ResourceState.loading())
                Log.d("INI LOGIN 1", "LOGIN 1")
                val response = registerLoginAccountRepo.loginAccountEmployee(request)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.data == null) {
                        _loginAccount.postValue(ResourceState.failured(responseBody?.message))
                    } else if (responseBody?.data?.role?.id != 4) {
                        _loginAccount.postValue(ResourceState.failured("Admin Tidak Diperbolehkan Disini"))
                    } else {
                        Log.d("INI LOGIN 2", "LOGIN 2")
                        _loginAccount.postValue(ResourceState.success(responseBody))
                    }
                } else {
                    _loginAccount.postValue(ResourceState.failured(response.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loginAccount.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }
        }
    }

    fun checkFormLiveData(idLogin: String) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                Log.d("INI LOGIN 3", "Login 3")
                _checkFormLiveData.postValue(ResourceState.loading())
                val response = registerLoginAccountRepo.findEmployeeByIdLogin(idLogin)
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if(responseBody?.code != 200) {
                        _checkFormLiveData.postValue(ResourceState.failured(responseBody?.message))
                    } else {
                        Log.d("INI LOGIN 3.1", "Login 3.1")
                        val employeeResponse = responseBody?.data
                        employeeResponse?.let {
                            val formIsNull = checkFormIsNull(
                                it.accountName, it.accountNumber, it.biologicalMothersName, it.bloodType,
                                it.dateOfBirth, it.emergencyNumber, it.fullname, it.gender,
                                it.ktpAddress, it.maritalStatus, it.nik, it.npwp,
                                it.npwpAddress, it.phoneNumber, it.placeOfBirth, it.postalCodeOfIdCard,
                                it.religion, it.residenceAddress, it.spouseName, it.numberOfChildren
                            )
                            sharedPreferences.edit().putString(AppConstant.APP_ID_EMPLOYEE, it.id).apply()
                            Log.d("Nilai Form Is Null", formIsNull.toString())
                            if (!formIsNull && it.verifiedHc == true) {
                                Log.d("Masuk nomer 1 Success", "1")
                                _checkFormLiveData.postValue(ResourceState.success(1)) //Sudah mengisi form dan diverifikasi oleh HC
                            } else if (!formIsNull && (it.verifiedHc == false || it.verifiedHc == null)) {
                                _checkFormLiveData.postValue(ResourceState.success(2)) //Sudah mengisi form tapi belum diverifikasi oleh HC
                            } else {
                                Log.d("Masuk nomer 3 Success", "3")
                                _checkFormLiveData.postValue(ResourceState.success(3)) //Belum mengisi form dan belum diverifikasi oleh HC
                            }
                        }
                    }
                } else {
                    _checkFormLiveData.postValue(ResourceState.failured(responseBody?.message as String?))
                }
            } catch (e : Exception) {
                e.printStackTrace()
                _checkFormLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }
        }
    }

    private fun checkFormIsNull(vararg input: Any?): Boolean {
        val check = ArrayList<Int>()
        for (i in input) {
            Log.d("I-nya adalahah", "$i")
            if (i == null) {
                check.add(1)
            }
        }
        if (check.size == 0) {
            return false //Logic ketika data semua sudah keisi
        }
        Log.d("check Array Validasi", "SIZE ARRAY ${check.size}")
        return check.size <= input.size
    }
}