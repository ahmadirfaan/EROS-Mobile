package com.finalproject.data.repositories.impl

import com.finalproject.data.api.RegisterLoginApi
import com.finalproject.data.models.account.LoginResponse
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.models.account.RegisterAccountResponse
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import retrofit2.Response
import javax.inject.Inject

class RegisterLoginAccountRepositoryImpl @Inject constructor(private val registerLoginApi: RegisterLoginApi) : RegisterLoginAccountRepository {
    override suspend fun createAccountEmployee(request: RegisterAccountRequest): Response<RegisterAccountResponse> {
        return registerLoginApi.createAccountEmployee(request)
    }

    override suspend fun loginAccountEmployee(request: RegisterAccountRequest): Response<LoginResponse> {
        return registerLoginApi.loginAccountEmployee(request)
    }

}