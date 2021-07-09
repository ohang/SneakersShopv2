package com.ohanyan.goro.sneakersshop.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SneakerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSneaker(sneaker: Sneaker)


    @Query("SELECT * FROM sneaker ORDER BY id ASC")
    fun readAllData(): LiveData<List<Sneaker>>

    @Query("DELETE  FROM sneaker")
     suspend fun deleteall()




}