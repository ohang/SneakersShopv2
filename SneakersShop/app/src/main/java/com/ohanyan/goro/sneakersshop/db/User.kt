package com.ohanyan.goro.sneakersshop.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "User")
data class User (
    @PrimaryKey(autoGenerate = true)
    var id:Int?=1,
    var name:String?="",
    var surname:String?="",
    var adress:String?="",
    var postcode:String?="",
    var phonenumber:String?="",
    var email:String?=""
): Parcelable
