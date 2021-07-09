package com.ohanyan.goro.shoppinglist.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ohanyan.goro.shoppinglist.data.db.entities.ShoppingItem


@Dao
interface ShoppingDao {


     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun upsert(item: ShoppingItem)


     @Delete
     suspend fun delete(item: ShoppingItem)

    @Query("SELECT * FROM SHOPPING_ITEM" )
     fun getAllShoppingItems():LiveData<List<ShoppingItem>>
}