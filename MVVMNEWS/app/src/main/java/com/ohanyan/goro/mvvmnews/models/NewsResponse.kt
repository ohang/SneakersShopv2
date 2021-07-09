package com.ohanyan.goro.mvvmnews.models

import com.ohanyan.goro.mvvmnews.models.Article

data class NewsResponse(
        val articles: List<Article>,
        val status: String,
        val totalResults: Int
)