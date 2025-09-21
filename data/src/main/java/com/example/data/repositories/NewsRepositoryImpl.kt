package com.example.data.repositories

import android.util.Log
import com.example.data.apis.NewsApiService
import com.example.data.mappers.NewsMapper
import com.example.domain.models.News
import com.example.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val mapper: NewsMapper
) :
    NewsRepository {
    override fun getTopHeadlines(country: String): Flow<List<News>> = flow {
        try {
            val response = newsApiService.getTopHeadlines(country, API_KEY)
            if (response.isSuccessful) {
                val newsList = response.body()?.articles?.map { mapper.toDomain(it) } ?: emptyList()
                emit(newsList)
            } else {
                // log full error body
                val errorBody = response.errorBody()?.string()
                throw Exception("API Error: $errorBody")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun searchNews(query: String): Flow<List<News>> = flow {
        try {
            val response = newsApiService.searchNews(query, API_KEY)
            if (response.isSuccessful) {
                val newsList = response.body()?.articles?.map { mapper.toDomain(it) } ?: emptyList()
                emit(newsList)
            } else {
                val errorBody = response.errorBody()?.string()
                throw Exception("API Error: $errorBody")
            }
        } catch (e: Exception) {
            throw e
        }
    }


    companion object {
        private const val API_KEY = "5bc8f27a8cd74ccbbf7a5d678cb7b9cd"
    }
}