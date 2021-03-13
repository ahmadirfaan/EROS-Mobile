package com.finalproject.di.module

import com.finalproject.data.repositories.impl.RegisterAccountRepositoryImpl
import com.finalproject.data.repositories.registeraccount.RegisterAccountRepository
import com.finalproject.di.qualifier.RegisterAccountRepoQualifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReimburseRepoModule {

    @Binds
    @RegisterAccountRepoQualifier
    abstract fun bindsRepoRegisterAccount(registerAccountRepositoryImpl: RegisterAccountRepositoryImpl): RegisterAccountRepository
}