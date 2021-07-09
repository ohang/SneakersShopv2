package com.ohanyan.goro.mvvmnews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ohanyan.goro.mvvmnews.models.Article
import retrofit2.http.DELETE

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article):Long

    @Query("SELECT * FROM articles")
    fun getAllData():LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)



}