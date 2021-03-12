package com.ohanyan.goro.sneakersshop.repositories

import androidx.lifecycle.LiveData
import com.ohanyan.goro.sneakersshop.db.Sneaker
import com.ohanyan.goro.sneakersshop.db.SneakerDao
import com.ohanyan.goro.sneakersshop.db.User
import com.ohanyan.goro.sneakersshop.db.UserDao

class SneakerRepository( val sneakerDao: SneakerDao, val userDao: UserDao){

    val readAllData:LiveData<List<Sneaker>> = sneakerDao.readAllData()

    suspend fun addSneaker(sneaker: Sneaker){
         sneakerDao.addSneaker(sneaker)
    }

    suspend fun addUser(user: User){
         userDao.addUser(user)
    }

   suspend fun getUserByEmail(email: String?):User{
        return  userDao.getUserByEmail(email)
    }



}