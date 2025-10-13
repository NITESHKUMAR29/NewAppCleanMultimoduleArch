package com.example.data.di

import com.example.data.mappers.NewsLocalMapper
import com.example.data.mappers.NewsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MapperModule {

    @Provides
    @Singleton
    fun provideNewsMapper(): NewsMapper = NewsMapper()

    @Singleton
    @Provides
    fun provideNewsLocalMapper(): NewsLocalMapper = NewsLocalMapper()

}