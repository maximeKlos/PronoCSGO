package com.example.testparsingetcompose.di

import android.content.Context
import com.example.testparsingetcompose.TestParsingEtCompose
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Singleton
    @Provides
    fun provideApplication(
        @ApplicationContext appContext: Context
    ): TestParsingEtCompose{
        return appContext as TestParsingEtCompose
    }
}