package com.finalproject.presentations.employee.claim.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.repositories.reimbursement.ReimbursementRepository
import com.finalproject.di.qualifier.ReimburseRepoQualifier
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetailReimbursementViewModel @Inject
constructor(
@ReimburseRepoQualifier
private val reimbursementRepository: ReimbursementRepository
) : ViewModel() {

    private var _uploadFileLiveData = MutableLiveData<ResourceState>()
    val uploadFileLiveData: LiveData<ResourceState>
        get() {
            return _uploadFileLiveData
        }

    private var _getURLFileLiveDataEmployee = MutableLiveData<ResourceState>()
    val getURLFileLiveDataEmployee : LiveData<ResourceState>
    get() {
        return  _getURLFileLiveDataEmployee
    }

    private var _getURLFileLiveDataAdmin = MutableLiveData<ResourceState>()
    val getURLFileLiveDataAdmin : LiveData<ResourceState>
        get() {
            return  _getURLFileLiveDataAdmin
        }

    fun uploadFile(idReimburse : String, pdfFile: File) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _uploadFileLiveData.postValue(ResourceState.loading())
                val response =  reimbursementRepository.updateFileIdReimburse(idReimburse, pdfFile)
                val responseBody = response?.body()
                if (response?.isSuccessful == true) {
                    if (responseBody?.code != 200) {
                        _uploadFileLiveData.postValue(ResourceState.failured(responseBody?.message))
                    } else {
                        _uploadFileLiveData.postValue(ResourceState.success(responseBody))
                    }
                } else {
                    _uploadFileLiveData.postValue(ResourceState.failured(response?.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uploadFileLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))

            }
        }
    }

    fun getURLDownloadFileEmployee(idReimburse : String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _getURLFileLiveDataEmployee.postValue(ResourceState.loading())
                val response =  reimbursementRepository.getURLFileEmployee(idReimburse)
                val responseBody = response?.body()
                if (response?.isSuccessful == true) {
                    if (responseBody?.code != 200) {
                        _getURLFileLiveDataEmployee.postValue(ResourceState.failured(responseBody?.message))
                    } else {
                        _getURLFileLiveDataEmployee.postValue(ResourceState.success(responseBody))
                    }
                } else {
                    _getURLFileLiveDataEmployee.postValue(ResourceState.failured(response?.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _getURLFileLiveDataEmployee.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))

            }
        }
    }

    fun getURLDownloadFileAdmin(idReimburse : String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _getURLFileLiveDataAdmin.postValue(ResourceState.loading())
                val response =  reimbursementRepository.getURLFileAdmin(idReimburse)
                val responseBody = response?.body()
                if (response?.isSuccessful == true) {
                    if (responseBody?.code != 200) {
                        _getURLFileLiveDataAdmin.postValue(ResourceState.failured(responseBody?.message))
                    } else {
                        _getURLFileLiveDataAdmin.postValue(ResourceState.success(responseBody))
                    }
                } else {
                    _getURLFileLiveDataAdmin.postValue(ResourceState.failured(response?.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _getURLFileLiveDataAdmin.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))

            }
        }
    }
}