package com.ohanyan.goro.sneakersshop.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM  User WHERE email =:inputemail")
    suspend fun getUserByEmail(inputemail: kotlin.String?): User

}