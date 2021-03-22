package com.finalproject.data.repositories.reimbursement

import com.finalproject.data.models.reimburse.ReimburseListByDateCategory
import com.finalproject.data.models.reimburse.ReimburseListByDateRequest
import com.finalproject.data.models.reimburse.ReimbursementList
import com.finalproject.data.models.reimburse.ReimbursementListRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReimbursementRepository {
    suspend fun getAllReimburse(request: ReimbursementListRequest): Response<ReimbursementList>

    suspend fun getAllReimburseByDate( request : ReimburseListByDateRequest) : Response<ReimbursementList>

    suspend fun getAllReimburseByDateAndCategory(request : ReimburseListByDateCategory) : Response<ReimbursementList>

}