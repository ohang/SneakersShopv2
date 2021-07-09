package com.ohanyan.goro.hayadaran.data

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val readAllData:LiveData<List<Book>> = bookDao.readAllData()



    suspend fun addBook(book:Book){
        bookDao.addBook(book)
    }

     fun searchData(searchstr:String?):LiveData<List<Book>>{
       return bookDao.searchData(searchstr)
    }


}