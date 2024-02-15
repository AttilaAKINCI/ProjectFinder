package com.akinci.projectfinder.di

import android.content.Context
import androidx.room.Room
import com.akinci.projectfinder.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TestDB

    @Provides
    @TestDB
    fun provideRoomDB(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context,
        AppDatabase::class.java
    ).allowMainThreadQueries().build()
}
