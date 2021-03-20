package com.finalproject.presentations.employee.history.success

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.reimburse.ReimbursementListRequest
import com.finalproject.data.models.reimburse.ReimbursementResponse
import com.finalproject.data.repositories.reimbursement.ReimbursementRepository
import com.finalproject.di.qualifier.ReimburseRepoQualifier
import com.finalproject.presentations.employee.history.HistoryClickListener
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistorySuccessViewModel @Inject
constructor(
    @ReimburseRepoQualifier
    private val reimbursementRepository: ReimbursementRepository
) : ViewModel(), HistoryClickListener {

    private var _reimburseListLiveData = MutableLiveData<ResourceState>()
    val reimburseListLiveData: LiveData<ResourceState>
        get() {
            return _reimburseListLiveData
        }

    private var _onDetailReimburseLiveData = MutableLiveData<ResourceState>()
    val onDetailReimburseLiveData: LiveData<ResourceState>
        get() {
            return _onDetailReimburseLiveData
        }

    fun getAllHistorySuccess(request: ReimbursementListRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _reimburseListLiveData.postValue(ResourceState.loading())
                val response = reimbursementRepository.getAllReimburse(request = request)
                val responseBody = response.body()!!
                if (response.isSuccessful) {
                    if (responseBody?.code != 200) {
                        _reimburseListLiveData.postValue(ResourceState.failured(responseBody.message))
                    } else {
                        val listHistory = responseBody?.data
                        val onSuccesHistory = listHistory?.filter {
                            it?.statusSuccess == true
                        }
                        if (onSuccesHistory.isNullOrEmpty()) {
                            _reimburseListLiveData.postValue(ResourceState.failured("Data Tidak ada"))
                        } else {
                            _reimburseListLiveData.postValue(ResourceState.success(onSuccesHistory))
                        }
                    }
                } else {
                    _reimburseListLiveData.postValue(ResourceState.failured(response.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onDetail(reimburse: ReimbursementResponse) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _onDetailReimburseLiveData.postValue(ResourceState.loading())
                _onDetailReimburseLiveData.postValue(ResourceState.success(reimburse))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}