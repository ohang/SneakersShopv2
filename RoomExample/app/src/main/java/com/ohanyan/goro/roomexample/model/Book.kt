package com.ohanyan.goro.roomexample.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "book_data")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Bookname:String,
    val Bookauthor:String
):Parcelable