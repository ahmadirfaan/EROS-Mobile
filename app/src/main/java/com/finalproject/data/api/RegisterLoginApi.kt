package com.finalproject.data.api

import com.finalproject.data.models.account.*
import retrofit2.Response
import retrofit2.http.*

interface RegisterLoginApi {

    @POST("/register/employee")
    suspend fun createAccountEmployee(@Body request : RegisterAccountRequest) : Response<RegisterAccountResponse>

    @POST("/login")
    suspend fun loginAccountEmployee(@Body request: RegisterAccountRequest) : Response<LoginResponse>

    @GET("/employee/idlogin/{idLogin}")
    suspend fun findEmployeeByIdLogin(@Path("idLogin") idLogin : String) : Response<FindByIdLoginEmployeeResponse>

    @PUT("/employee/editform/{idEmployee}")
    suspend fun editFormEmployeeProfile(@Path("idEmployee") idEmployee : String, @Body request : FormAccountRequest) : Response<FindByIdLoginEmployeeResponse>

    @PUT("/employee/ganti-password")
    suspend fun changePasswordByIdLogin(@Body request: ChangePasswordRequest) : Response<LoginResponse>

    @POST("/forgot-password")
    suspend fun forgotPassword(@Body request : ForgotPasswordRequest) : Response<ForgotPasswordResponse>
}