package com.ohanyan.goro.sneakersshop.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Sneaker")
data class Sneaker(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=1,
    var price: String?="12000",
    var mainImgUrl:String="",
    var description:String="",
    var name: String?="Nikes"):Parcelable {

}

