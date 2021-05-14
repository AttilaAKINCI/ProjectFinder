package com.akinci.projectfinder.features.repocommon.di

import android.content.Context
import androidx.room.Room
import com.akinci.projectfinder.common.network.NetworkChecker
import com.akinci.projectfinder.common.room.RoomConfig.Companion.DB_NAME
import com.akinci.projectfinder.features.repocommon.data.api.RepoServiceDao
import com.akinci.projectfinder.features.repocommon.data.local.RepoDatabase
import com.akinci.projectfinder.features.repocommon.data.local.dao.RepoDao
import com.akinci.projectfinder.features.repocommon.repository.RepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonFeatureModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            RepoDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideRepoDao(db: RepoDatabase) = db.getRepoDao()


    @Provides
    @Singleton
    fun provideRepoServiceDao(
        retrofit: Retrofit
    ): RepoServiceDao = retrofit.create(RepoServiceDao::class.java)

    @Provides
    @Singleton
    fun provideRepoRepository(
        repoServiceDao: RepoServiceDao,
        repoDao: RepoDao,
        networkChecker: NetworkChecker
    ) = RepoRepository(repoServiceDao, repoDao, networkChecker)


}