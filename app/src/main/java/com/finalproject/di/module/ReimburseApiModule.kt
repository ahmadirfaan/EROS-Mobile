package com.finalproject.di.module

import com.finalproject.data.api.RegisterLoginApi
import com.finalproject.data.api.ReimburseApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReimburseApiModule {

    @Singleton
    @Provides
    fun provideRegisterLoginAccountApi(retrofit : Retrofit) = retrofit.create(RegisterLoginApi::class.java)

    @Singleton
    @Provides
    fun providesReimbursementApi(retrofit: Retrofit) = retrofit.create(ReimburseApi::class.java)
}