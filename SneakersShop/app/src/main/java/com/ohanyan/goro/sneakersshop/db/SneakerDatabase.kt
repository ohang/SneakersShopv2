package com.ohanyan.goro.sneakersshop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sneaker::class,User::class],version = 3,exportSchema = false)
abstract  class SneakerDatabase:RoomDatabase() {

    abstract fun sneakDao():SneakerDao
    abstract fun userDao():UserDao

    companion object {
        @Volatile
        private var INSTANCE:SneakerDatabase?=null

        fun getDatabase(context: Context):SneakerDatabase {

            val tempinstance= INSTANCE

            if (tempinstance!=null){
                return tempinstance
            }

            synchronized(this){
                val instance= Room.databaseBuilder(context.applicationContext,SneakerDatabase::class.java,
                    "sneakDB").build()
                INSTANCE=instance
                return instance
            }
        }






    }


}