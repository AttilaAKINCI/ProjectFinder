package com.akinci.projectfinder.core.di

import com.akinci.projectfinder.core.coroutine.ContextProvider
import com.akinci.projectfinder.core.coroutine.ContextProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContextProvider(): ContextProvider = ContextProviderImpl()
}