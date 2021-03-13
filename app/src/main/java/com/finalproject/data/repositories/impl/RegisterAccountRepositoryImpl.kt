package com.finalproject.data.repositories.impl

import com.finalproject.data.api.RegisterApi
import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.models.account.RegisterAccountResponse
import com.finalproject.data.repositories.registeraccount.RegisterAccountRepository
import retrofit2.Response
import javax.inject.Inject

class RegisterAccountRepositoryImpl @Inject constructor(private val registerApi: RegisterApi) : RegisterAccountRepository {
    override suspend fun createAccountEmployee(request: RegisterAccountRequest): Response<RegisterAccountResponse> {
        return registerApi.createAccountEmployee(request)
    }

}