package com.ohanyan.goro.hayadaran.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "book_data")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val Title:String,
    val Author:String,
    val Description:String,
    val ImgURL:String,
    val LinkURL:String
):Parcelable