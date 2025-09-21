package com.example.domain.models

data class News(
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val url: String?,
    val publishedAt: String?
)
