package com.example.domain.useCases

import com.example.domain.models.News
import com.example.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(private val newsRepository: NewsRepository){
    operator fun invoke(country: String): Flow<List<News>> = newsRepository.getTopHeadlines(country)
}