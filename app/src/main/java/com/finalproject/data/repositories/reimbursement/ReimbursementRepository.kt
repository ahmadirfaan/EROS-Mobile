package com.finalproject.data.repositories.reimbursement

import com.finalproject.data.models.reimburse.ReimbursementList
import com.finalproject.data.models.reimburse.ReimbursementListRequest
import retrofit2.Response
import retrofit2.http.POST

interface ReimbursementRepository {
    @POST("/reimburse/filter-category-employee")
    suspend fun getAllReimburse(request: ReimbursementListRequest): Response<ReimbursementList>

}