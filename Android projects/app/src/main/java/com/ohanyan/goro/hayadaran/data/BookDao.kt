package com.ohanyan.goro.hayadaran.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addBook(book:Book)

    @Query("SELECT * FROM book_data ORDER BY id ASC")
    fun readAllData(): LiveData<List<Book>>

    @Query("SELECT * FROM book_data  WHERE Title LIKE '%' ||:searchstr || '%' OR Author LIKE '%' ||:searchstr || '%' ")
    fun searchData( searchstr:String?): LiveData<List<Book>>

}

