package com.finalproject.data.repositories.registeraccount

import com.finalproject.data.models.account.RegisterAccountRequest
import com.finalproject.data.models.account.RegisterAccountResponse
import retrofit2.Response

interface RegisterAccountRepository {

    suspend fun createAccountEmployee(request: RegisterAccountRequest): Response<RegisterAccountResponse>
}