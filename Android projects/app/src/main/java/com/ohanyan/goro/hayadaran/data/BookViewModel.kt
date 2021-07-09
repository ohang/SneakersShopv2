package com.ohanyan.goro.hayadaran.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application):AndroidViewModel(application) {

     val readAllData:LiveData<List<Book>>

    private val repository:BookRepository

    init {
        val bookDao=BookDatabase.getDatabase(application).bookDao()
        repository= BookRepository(bookDao)
        readAllData=repository.readAllData


    }




    fun addBook(book:Book){
        viewModelScope.launch(Dispatchers.IO) {

            repository.addBook(book)
        }
    }

    fun searchData(searchstr:String?):LiveData<List<Book>>{


         return   repository.searchData(searchstr)

    }


}