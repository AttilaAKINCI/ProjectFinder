package com.akinci.projectfinder.data.di

import android.content.Context
import androidx.room.Room
import com.akinci.projectfinder.core.application.AppConfig
import com.akinci.projectfinder.core.network.HttpClientFactory
import com.akinci.projectfinder.core.network.HttpEngineFactory
import com.akinci.projectfinder.data.room.favorite.FavoriteDao
import com.akinci.projectfinder.data.room.AppDatabase
import com.akinci.projectfinder.data.room.AppDatabaseKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideApiClient(
        httpEngineFactory: HttpEngineFactory,
        appConfig: AppConfig,
    ): HttpClient = HttpClientFactory(
        httpEngineFactory,
        appConfig,
    ).create()

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabaseKeys.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun getFavoriteDao(database: AppDatabase): FavoriteDao = database.getFavoriteDao()
}