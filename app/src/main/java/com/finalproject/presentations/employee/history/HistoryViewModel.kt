package com.finalproject.presentations.employee.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finalproject.data.models.reimburse.*
import com.finalproject.data.repositories.reimbursement.ReimbursementRepository
import com.finalproject.di.qualifier.ReimburseRepoQualifier
import com.finalproject.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.Body
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject
constructor(
    @ReimburseRepoQualifier
    private val reimbursementRepository: ReimbursementRepository
) : ViewModel(), HistoryClickListener{


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


    fun getAllHistoryProgressByCategory(request: ReimbursementListRequest) {
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
                        val onProgressHistory = listHistory?.filter {
                            it?.statusSuccess != true && it?.statusReject != true
                        }
                        if (onProgressHistory.isNullOrEmpty()) {
                            _reimburseListLiveData.postValue(ResourceState.failured("Data Tidak ada"))
                        } else {
                            _reimburseListLiveData.postValue(ResourceState.success(onProgressHistory))
                        }
                    }
                } else {
                    _reimburseListLiveData.postValue(ResourceState.failured(response.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _reimburseListLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

    fun getAllHistoryProgressByDate(request: ReimburseListByDateRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _reimburseListLiveData.postValue(ResourceState.loading())
                val response = reimbursementRepository.getAllReimburseByDate(request = request)
                val responseBody = response.body()!!
                if (response.isSuccessful) {
                    if (responseBody?.code != 200) {
                        _reimburseListLiveData.postValue(ResourceState.failured(responseBody.message))
                    } else {
                        val listHistory = responseBody?.data
                        val onProgressHistory = listHistory?.filter {
                            it?.statusSuccess != true && it?.statusReject != true
                        }
                        if (onProgressHistory.isNullOrEmpty()) {
                            _reimburseListLiveData.postValue(ResourceState.failured("Data Tidak ada"))
                        } else {
                            _reimburseListLiveData.postValue(ResourceState.success(onProgressHistory))
                        }
                    }
                } else {
                    _reimburseListLiveData.postValue(ResourceState.failured(response.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _reimburseListLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

    fun getAllHistoryProgressByDateCategory(request: ReimburseListByDateCategory) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _reimburseListLiveData.postValue(ResourceState.loading())
                val response = reimbursementRepository.getAllReimburseByDateAndCategory(request = request)
                val responseBody = response.body()!!
                if (response.isSuccessful) {
                    if (responseBody?.code != 200) {
                        _reimburseListLiveData.postValue(ResourceState.failured(responseBody.message))
                    } else {
                        val listHistory = responseBody?.data
                        val onProgressHistory = listHistory?.filter {
                            it?.statusSuccess != true && it?.statusReject != true

                        }
                        if (onProgressHistory.isNullOrEmpty()) {
                            _reimburseListLiveData.postValue(ResourceState.failured("Data Tidak ada"))
                        } else {
                            _reimburseListLiveData.postValue(ResourceState.success(onProgressHistory))
                        }
                    }
                } else {
                    _reimburseListLiveData.postValue(ResourceState.failured(response.message()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _reimburseListLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

    fun getAllHistorySuccessByCategory(request: ReimbursementListRequest) {
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
                            it?.statusSuccess == true || it?.statusReject == true
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
                _reimburseListLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

    fun getAllHistorySuccessByDate(request: ReimburseListByDateRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _reimburseListLiveData.postValue(ResourceState.loading())
                val response = reimbursementRepository.getAllReimburseByDate(request = request)
                val responseBody = response.body()!!
                if (response.isSuccessful) {
                    if (responseBody?.code != 200) {
                        _reimburseListLiveData.postValue(ResourceState.failured(responseBody.message))
                    } else {
                        val listHistory = responseBody?.data
                        val onSuccesHistory = listHistory?.filter {
                            it?.statusSuccess == true || it?.statusReject == true
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
                _reimburseListLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

    fun getAllHistorySuccessByDateCategory(request: ReimburseListByDateCategory) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _reimburseListLiveData.postValue(ResourceState.loading())
                val response = reimbursementRepository.getAllReimburseByDateAndCategory(request = request)
                val responseBody = response.body()!!
                if (response.isSuccessful) {
                    if (responseBody?.code != 200) {
                        _reimburseListLiveData.postValue(ResourceState.failured(responseBody.message))
                    } else {
                        val listHistory = responseBody?.data
                        val onSuccesHistory = listHistory?.filter {
                            it?.statusSuccess == true || it?.statusReject == true
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
                _reimburseListLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }

        }
    }

    fun getAllReimburseByIdEmployeeOnProgress(request: ReimburseListByEmployeeId) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _reimburseListLiveData.postValue(ResourceState.loading())
                val response = reimbursementRepository.getAllReimburseByIdEmployee(request = request)
                val responseBody = response.body()!!
                if (response.isSuccessful) {
                    Log.d("Response", responseBody.toString())
                    if (responseBody?.code != 200) {
                        _reimburseListLiveData.postValue(ResourceState.failured(responseBody.message))
                    } else {
                        val listHistory = responseBody?.data
                        val onSuccesHistory = listHistory?.filter {
                            it?.statusSuccess != true && it?.statusReject != true
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
                _reimburseListLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }
        }
    }

    fun getAllReimburseByIdEmployeeOnSuccess(request: ReimburseListByEmployeeId) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _reimburseListLiveData.postValue(ResourceState.loading())
                val response = reimbursementRepository.getAllReimburseByIdEmployee(request = request)
                val responseBody = response.body()!!
                if (response.isSuccessful) {
                    if (responseBody?.code != 200) {
                        _reimburseListLiveData.postValue(ResourceState.failured(responseBody.message))
                    } else {
                        val listHistory = responseBody?.data
                        val onSuccesHistory = listHistory?.filter {
                            it?.statusSuccess == true || it?.statusReject == true
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
                _reimburseListLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
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
                _onDetailReimburseLiveData.postValue(ResourceState.failured("Mohon Maaf Aplikasi Sedang Bermasalah :D"))
            }
        }
    }
}