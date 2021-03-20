package com.finalproject.data.api

import com.finalproject.data.models.reimburse.ReimbursementList
import com.finalproject.data.models.reimburse.ReimbursementListRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReimburseApi {
    @POST("/reimburse/filter-category-employee")
    suspend fun getAllReimburse(@Body request : ReimbursementListRequest) : Response<ReimbursementList>
}