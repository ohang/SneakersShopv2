package com.ohanyan.goro.roomexample.data

import android.content.Context
import androidx.room.*
import com.ohanyan.goro.roomexample.model.Book

@Database(entities = [Book::class],version = 1,exportSchema = false)
abstract class BookDatabase:RoomDatabase(){

    abstract fun bookDao():BookDao

    companion object {

        @Volatile
        private var INSTANCE:BookDatabase?=null

        fun getDatabase(context: Context):BookDatabase {
             val tempinstance= INSTANCE

             if (tempinstance!=null){
                 return tempinstance
             }


             synchronized(this){
                 val instance= Room.databaseBuilder(context.applicationContext,BookDatabase::class.java,
                 "book_database").build()
                 INSTANCE=instance
                 return instance
             }

        }

    }

}