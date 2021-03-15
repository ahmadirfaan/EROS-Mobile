package com.finalproject.data.repositories.registeraccount

import com.finalproject.data.models.account.LoginResponse
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.models.account.RegisterAccountResponse
import retrofit2.Response
import retrofit2.http.Body

interface RegisterLoginAccountRepository {

    suspend fun createAccountEmployee(request: RegisterAccountRequest): Response<RegisterAccountResponse>
    suspend fun loginAccountEmployee(request: RegisterAccountRequest) : Response<LoginResponse>

}