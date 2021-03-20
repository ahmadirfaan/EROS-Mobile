package com.finalproject.data.repositories.impl

import com.finalproject.data.api.RegisterLoginApi
import com.finalproject.data.models.account.*
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

    override suspend fun findEmployeeByIdLogin(idLogin: String): Response<FindByIdLoginEmployeeResponse> {
        return registerLoginApi.findEmployeeByIdLogin(idLogin)
    }

    override suspend fun editFormEmployeeProfile(idEmployee: String, request: FormAccountRequest): Response<FindByIdLoginEmployeeResponse> {
        return registerLoginApi.editFormEmployeeProfile(idEmployee, request)
    }

    override suspend fun changePasswordByIdLogin(request: ChangePasswordRequest): Response<LoginResponse> {
        return registerLoginApi.changePasswordByIdLogin(request)
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequest): Response<ForgotPasswordResponse> {
        return registerLoginApi.forgotPassword(request)
    }

}