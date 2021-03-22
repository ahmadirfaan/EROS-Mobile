package com.finalproject.data.repositories.impl

import com.finalproject.data.api.RegisterLoginApi
import com.finalproject.data.api.ReimburseApi
import com.finalproject.data.models.reimburse.*
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.data.repositories.reimbursement.ReimbursementRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class ReimbursementRepositoryImpl @Inject constructor(
    private val reimburseApi: ReimburseApi) : ReimbursementRepository {

    override suspend fun getAllReimburse(request: ReimbursementListRequest): Response<ReimbursementList> {
        return reimburseApi.getAllReimburse(request)
    }

    override suspend fun getAllReimburseByDate(request: ReimburseListByDateRequest): Response<ReimbursementList> {
        return reimburseApi.getAllReimburseByDate(request)

    }

    override suspend fun getAllReimburseByDateAndCategory(request: ReimburseListByDateCategory): Response<ReimbursementList> {
        return reimburseApi.getAllReimburseByDateAndCategory(request)
    }

    override suspend fun addReimbursement(request: AddReimbursementRequest): Response<AddReimbursementResponse> {
        return reimburseApi.addReimbursement(request)
    }

    override suspend fun uploadFileIdReimburse(idReimburse: String, pdfFile: File) : Response<UploadResponse> {
        val mediaType = MediaType.parse("multipart/form-data")
        val requestFile = RequestBody.create(mediaType, pdfFile);
        val fileToUpload = MultipartBody.Part.createFormData("file", pdfFile.getName(), requestFile);
        return reimburseApi.uploadFileIdReimburse(idReimburse, fileToUpload)
    }
}