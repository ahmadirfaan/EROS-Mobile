package com.finalproject.data.api

import com.finalproject.data.models.reimburse.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ReimburseApi {
    @POST("/reimburse/filter-category-employee")
    suspend fun getAllReimburse(@Body request : ReimbursementListRequest) : Response<ReimbursementList>

    @POST("/reimburse/filter-date-employee")
    suspend fun getAllReimburseByDate(@Body request : ReimburseListByDateRequest) : Response<ReimbursementList>

    @POST("/reimburse/filter-date-category-employee")
    suspend fun getAllReimburseByDateAndCategory(@Body request : ReimburseListByDateCategory) : Response<ReimbursementList>

    @POST("/reimburse")
    suspend fun addReimbursement(@Body request : AddReimbursementRequest) : Response<AddReimbursementResponse>

    @Multipart
    @POST("/bill/{idReimburse}/upload/file")
    suspend fun uploadFileIdReimburse(@Path("idReimburse") idReimburse : String, @Part filePdf : MultipartBody.Part) : Response<UploadResponse>
}