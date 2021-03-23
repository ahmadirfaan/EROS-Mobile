package com.finalproject.presentations.employee.claim

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.reimburse.AddReimbursementRequest
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
class ClaimViewModel @Inject
constructor(
    @ReimburseRepoQualifier
    private val reimbursementRepository: ReimbursementRepository
) : ViewModel() {

    private var idReimburseData: String? = null

    private var _addReimbursementLiveData = MutableLiveData<ResourceState>()
    val addReimbursementLiveData: LiveData<ResourceState>
        get() {
            return _addReimbursementLiveData
        }

    private var _uploadFileLiveData = MutableLiveData<ResourceState>()
    val uploadFileLiveData: LiveData<ResourceState>
        get() {
            return _uploadFileLiveData
        }

    fun addReimbursement(request: AddReimbursementRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _addReimbursementLiveData.postValue(ResourceState.loading())
                val response = reimbursementRepository.addReimbursement(request)
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if (responseBody?.code != 200) {
                        _addReimbursementLiveData.postValue(ResourceState.failured(responseBody?.message))
                    } else {
                        val reimburse = responseBody?.data
                        _addReimbursementLiveData.postValue(ResourceState.success(reimburse))
                        idReimburseData = reimburse?.id
                    }
                } else {
                    _addReimbursementLiveData.postValue(ResourceState.failured(response.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _addReimbursementLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }
        }
    }

    fun uploadFile(pdfFile: File) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _uploadFileLiveData.postValue(ResourceState.loading())
                val response = idReimburseData?.let { reimbursementRepository.uploadFileIdReimburse(it, pdfFile) }
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
}