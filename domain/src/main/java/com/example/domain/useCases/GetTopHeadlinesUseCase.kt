package com.example.domain.useCases

import com.example.domain.models.News
import com.example.domain.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(private val newsRepository: NewsRepository){
    operator fun invoke(country: String): Flow<List<News>> = newsRepository.getTopHeadlines(country).flowOn(Dispatchers.IO)
}