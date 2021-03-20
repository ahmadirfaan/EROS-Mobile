package com.finalproject.data.repositories.impl

import com.finalproject.data.api.RegisterLoginApi
import com.finalproject.data.api.ReimburseApi
import com.finalproject.data.models.reimburse.ReimbursementList
import com.finalproject.data.models.reimburse.ReimbursementListRequest
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.data.repositories.reimbursement.ReimbursementRepository
import retrofit2.Response
import javax.inject.Inject

class ReimbursementRepositoryImpl @Inject constructor(
    private val reimburseApi: ReimburseApi) : ReimbursementRepository {

    override suspend fun getAllReimburse(request: ReimbursementListRequest): Response<ReimbursementList> {
        return reimburseApi.getAllReimburse(request)
    }
}