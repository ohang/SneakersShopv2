package com.ohanyan.goro.roomexample.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Update
import com.ohanyan.goro.roomexample.data.BookDatabase
import com.ohanyan.goro.roomexample.repository.BookRepository
import com.ohanyan.goro.roomexample.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application):AndroidViewModel(application) {

     val readAllData:LiveData<List<Book>>
    private val repository: BookRepository

    init {
        val bookDao=
            BookDatabase.getDatabase(
                application
            ).bookDao()
        repository=
            BookRepository(bookDao)
        readAllData=repository.readAllData


    }

    fun addBook(book: Book){
        viewModelScope.launch(Dispatchers.IO) {

            repository.addBook(book)
        }
    }

    fun UpdateBook(book: Book){
        viewModelScope.launch(Dispatchers.IO) {

            repository.UpdateBook(book)
        }
    }
}