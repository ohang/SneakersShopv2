package com.ohanyan.goro.mvvmnews.repository

import com.ohanyan.goro.mvvmnews.api.RetrofitInstance
import com.ohanyan.goro.mvvmnews.db.ArticleDatabase
import com.ohanyan.goro.mvvmnews.models.Article
import retrofit2.Retrofit

class NewsRepository(val db:ArticleDatabase) {

    suspend fun getBreakingNews(countrycode:String,pageNumber:Int)=
            RetrofitInstance.api.getBreakingNews(countrycode,pageNumber)

    suspend fun searchNews(query:String,pageNumber:Int)=
            RetrofitInstance.api.searchForNews(query,pageNumber)

    suspend fun upsert(article:Article)=db.getArticleDao().upsert(article)

    fun getSavedNews()=db.getArticleDao().getAllData()

    suspend fun delete(article: Article)=db.getArticleDao().deleteArticle(article)
}