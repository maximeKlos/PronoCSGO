package com.example.testparsingetcompose.di

import com.example.testparsingetcompose.data.MatchesDataSource
import com.example.testparsingetcompose.data.MatchesRepository
import com.example.testparsingetcompose.data.Repository
import com.example.testparsingetcompose.data.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @AppModule.RemoteDataSource
    @Provides
    fun providesRemoteDataSource(): MatchesDataSource {
        return RemoteDataSource()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTasksRepository(
        @AppModule.RemoteDataSource remoteTaskDataSource: MatchesDataSource
    ): Repository {
        return MatchesRepository(
            remoteTaskDataSource
        )
    }
}