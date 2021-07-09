package com.ohanyan.goro.roomexample.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ohanyan.goro.roomexample.model.Book

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBook(book: Book)

    @Update
    suspend fun UpdateBook(book: Book)

    @Query("SELECT * FROM book_data ORDER BY id ASC")
    fun readAllData(): LiveData<List<Book>>


}