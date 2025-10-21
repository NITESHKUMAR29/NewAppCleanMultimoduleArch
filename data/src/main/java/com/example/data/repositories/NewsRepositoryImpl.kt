package com.example.data.repositories

import com.example.data.apis.NewsApiService
import com.example.data.local.dao.NewsDao
import com.example.data.mappers.NewsLocalMapper
import com.example.data.mappers.NewsMapper
import com.example.domain.models.News
import com.example.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val mapper: NewsMapper,
    private val localMapper: NewsLocalMapper,
    private val newsDao: NewsDao
) :
    NewsRepository {
    override fun getTopHeadlines(country: String): Flow<List<News>> = flow {
        //  Emit cached data first (offline-first)
        val cachedNews = newsDao.getAllNews().firstOrNull()
        if (!cachedNews.isNullOrEmpty()) {
            emit(cachedNews.map { localMapper.toDomain(it) })
        }

        try {
            //  Fetch latest from network
            val response = newsApiService.getTopHeadlines(country, API_KEY)
            if (response.isSuccessful) {
                val newsList = response.body()?.articles?.map { mapper.toDomain(it) } ?: emptyList()

                //  Cache the latest response
                newsDao.clearAll()
                newsDao.insertAll(newsList.map { localMapper.toEntity(it) })

                emit(newsList)
            } else {
                val errorBody = response.errorBody()?.string()
                throw Exception("API Error: $errorBody")
            }
        } catch (e: Exception) {
            //  On network error, emit cached data (if available)
            val localData = newsDao.getAllNews().firstOrNull()
            if (!localData.isNullOrEmpty()) {
                emit(localData.map { localMapper.toDomain(it) })
            } else {
                throw e
            }
        }
    }

    override fun searchNews(query: String): Flow<List<News>> {
        return flow {
            try {
                val response = newsApiService.searchNews(query, API_KEY)
                if (response.isSuccessful) {
                    val newsList =
                        response.body()?.articles?.map { mapper.toDomain(it) } ?: emptyList()
                    emit(newsList)
                } else {
                    val errorBody = response.errorBody()?.string()
                    throw Exception("API Error: $errorBody")
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }


    companion object {
        private const val API_KEY = "5bc8f27a8cd74ccbbf7a5d678cb7b9cd"
    }
}