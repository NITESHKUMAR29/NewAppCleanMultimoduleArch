package com.example.data.apis

import com.example.data.dtos.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): Response<NewsResponseDto>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponseDto>
}