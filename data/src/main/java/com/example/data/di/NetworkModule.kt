package com.example.data.di

import com.example.data.apis.NewsApiService
import com.example.data.mappers.NewsMapper
import com.example.data.repositories.NewsRepositoryImpl
import com.example.domain.repositories.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideNewsRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl("https://newsapi.org/").addConverterFactory(
            GsonConverterFactory.create()
        ).build()

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService =
        retrofit.create(NewsApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsMapper(): NewsMapper = NewsMapper()


}