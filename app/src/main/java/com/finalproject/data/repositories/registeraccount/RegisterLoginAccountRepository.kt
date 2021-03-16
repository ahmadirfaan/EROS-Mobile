package com.finalproject.data.repositories.registeraccount

import com.finalproject.data.models.account.FindByIdLoginEmployeeResponse
import com.finalproject.data.models.account.LoginResponse
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.models.account.RegisterAccountResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface RegisterLoginAccountRepository {

    suspend fun createAccountEmployee(request: RegisterAccountRequest): Response<RegisterAccountResponse>
    suspend fun loginAccountEmployee(request: RegisterAccountRequest) : Response<LoginResponse>
    suspend fun findEmployeeByIdLogin(idLogin : String) : Response<FindByIdLoginEmployeeResponse>
}