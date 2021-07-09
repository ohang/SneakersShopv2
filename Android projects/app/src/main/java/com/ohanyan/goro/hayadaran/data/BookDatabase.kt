package com.ohanyan.goro.hayadaran.data

import android.content.Context
import android.widget.Toast
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.ohanyan.goro.hayadaran.MainActivity
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.net.ContentHandler

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
                "booksDB").createFromAsset("booksDB").build()
                 INSTANCE=instance
                 return instance
             }

        }






}


}