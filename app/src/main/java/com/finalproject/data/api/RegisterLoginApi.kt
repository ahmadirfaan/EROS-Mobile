package com.finalproject.data.api

import com.finalproject.data.models.account.FindByIdLoginEmployeeResponse
import com.finalproject.data.models.account.LoginResponse
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.models.account.RegisterAccountResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterLoginApi {

    @POST("/register/employee")
    suspend fun createAccountEmployee(@Body request : RegisterAccountRequest) : Response<RegisterAccountResponse>

    @POST("/login")
    suspend fun loginAccountEmployee(@Body request: RegisterAccountRequest) : Response<LoginResponse>

    @GET("/employee/idlogin/{idLogin}")
    suspend fun findEmployeeByIdLogin(@Path("idLogin") idLogin : String) : Response<FindByIdLoginEmployeeResponse>
}