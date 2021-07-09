package com.ohanyan.goro.sneakersshop.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.db.SneakerDatabase
import com.ohanyan.goro.sneakersshop.db.User
import com.ohanyan.goro.sneakersshop.repositories.SneakerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SneakerViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Sneaker>>

    private val repository: SneakerRepository

    init {

        val sneakerDao = SneakerDatabase.getDatabase(application).sneakDao()
        val userDao = SneakerDatabase.getDatabase(application).userDao()
        repository = SneakerRepository(sneakerDao, userDao)
        readAllData = repository.readAllData

    }


    fun addSneaker(sneaker: Sneaker) {
        viewModelScope.launch(Dispatchers.IO) {

            repository.addSneaker(sneaker)
        }
    }

    fun delete() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteall()
        }
    }


    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {

            repository.addUser(user)
        }
    }

    suspend fun getUserByEmail(email: String?): User {
        return repository.getUserByEmail(email)

    }


}

