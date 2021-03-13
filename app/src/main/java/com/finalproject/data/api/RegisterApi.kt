package com.finalproject.data.api

import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.models.account.RegisterAccountResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {

    @POST("/register/employee")
    suspend fun createAccountEmployee(@Body request : RegisterAccountRequest) : Response<RegisterAccountResponse>
}