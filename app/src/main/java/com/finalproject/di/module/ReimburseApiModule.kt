package com.finalproject.di.module

import com.finalproject.data.api.RegisterApi
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
    fun provideRegisterAccountApi(retrofit : Retrofit) = retrofit.create(RegisterApi::class.java)
}