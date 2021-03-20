package com.finalproject.di.module

import com.finalproject.data.repositories.impl.RegisterLoginAccountRepositoryImpl
import com.finalproject.data.repositories.impl.ReimbursementRepositoryImpl
import com.finalproject.data.repositories.registeraccount.RegisterLoginAccountRepository
import com.finalproject.data.repositories.reimbursement.ReimbursementRepository
import com.finalproject.di.qualifier.RegisterLoginAccountRepoQualifier
import com.finalproject.di.qualifier.ReimburseRepoQualifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReimburseRepoModule {

    @Binds
    @RegisterLoginAccountRepoQualifier
    abstract fun bindsRepoRegisterLoginAccount(registerAccountLoginRepositoryImpl: RegisterLoginAccountRepositoryImpl): RegisterLoginAccountRepository

    @Binds
    @ReimburseRepoQualifier
    abstract fun bindsRepoReimbursement(reimbursementRepositoryImpl: ReimbursementRepositoryImpl) : ReimbursementRepository
}