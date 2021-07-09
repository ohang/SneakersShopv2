package com.ohanyan.goro.sneakersshop.repositories

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.db.SneakerDao
import com.ohanyan.goro.sneakersshop.db.User
import com.ohanyan.goro.sneakersshop.db.UserDao
import kotlinx.coroutines.tasks.await

class SneakerRepository( val sneakerDao: SneakerDao, val userDao: UserDao){


    val readAllData:LiveData<List<Sneaker>> = sneakerDao.readAllData()

    suspend fun addSneaker(sneaker: Sneaker){
         sneakerDao.addSneaker(sneaker)
    }

    suspend fun deleteall(){
        sneakerDao.deleteall()
    }

    suspend fun addUser(user: User){
         userDao.addUser(user)
    }

   suspend fun getUserByEmail(email: String?):User{
        return  userDao.getUserByEmail(email)
    }



    }



