package com.finalproject.data.repositories.reimbursement

import com.finalproject.data.models.reimburse.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface ReimbursementRepository {
    suspend fun getAllReimburse(request: ReimbursementListRequest): Response<ReimbursementList>

    suspend fun getAllReimburseByDate( request : ReimburseListByDateRequest) : Response<ReimbursementList>

    suspend fun getAllReimburseByDateAndCategory(request : ReimburseListByDateCategory) : Response<ReimbursementList>

    suspend fun addReimbursement(request : AddReimbursementRequest) : Response<AddReimbursementResponse>

//    suspend fun uploadFileIdReimburse(idReimburse : String, pdfFile : File) : Response<UploadResponse>

    suspend fun updateFileIdReimburse(idReimburse : String, filePdf : File) : Response<UploadResponse>

    suspend fun getAllReimburseByIdEmployee(request: ReimburseListByEmployeeId) : Response<ReimbursementList>

    suspend fun getURLFileAdmin(idReimburse: String) : Response<BillResponse>

    suspend fun getURLFileEmployee(idReimburse: String) : Response<BillResponse>

}