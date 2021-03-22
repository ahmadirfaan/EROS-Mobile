package com.finalproject.data.repositories.reimbursement

import com.finalproject.data.models.reimburse.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import java.io.File

interface ReimbursementRepository {
    suspend fun getAllReimburse(request: ReimbursementListRequest): Response<ReimbursementList>

    suspend fun getAllReimburseByDate( request : ReimburseListByDateRequest) : Response<ReimbursementList>

    suspend fun getAllReimburseByDateAndCategory(request : ReimburseListByDateCategory) : Response<ReimbursementList>

    suspend fun addReimbursement(request : AddReimbursementRequest) : Response<AddReimbursementResponse>

    suspend fun uploadFileIdReimburse(idReimburse : String, pdfFile : File) : Response<UploadResponse>

}