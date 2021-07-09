package com.ohanyan.goro.roomexample.repository

import androidx.lifecycle.LiveData
import com.ohanyan.goro.roomexample.data.BookDao
import com.ohanyan.goro.roomexample.model.Book

class BookRepository(private val bookDao: BookDao) {

    val readAllData:LiveData<List<Book>> = bookDao.readAllData()

    suspend fun addBook(book: Book){
        bookDao.addBook(book)
    }

    suspend fun UpdateBook(book: Book){
        bookDao.UpdateBook(book)
    }

}