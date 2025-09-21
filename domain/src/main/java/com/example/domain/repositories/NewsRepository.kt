package com.example.domain.repositories

import com.example.domain.models.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(country: String): Flow<List<News>>
    fun searchNews(query: String): Flow<List<News>>
}