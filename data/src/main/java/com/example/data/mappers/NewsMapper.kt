package com.example.data.mappers

import com.example.data.dtos.ArticleDto
import com.example.domain.models.News


class NewsMapper {
    fun toDomain(article: ArticleDto): News = News(
        title = article.title ?: "",
        description = article.description,
        imageUrl = article.urlToImage,
        url = article.url,
        publishedAt = article.publishedAt
    )
}