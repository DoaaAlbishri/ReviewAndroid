package com.example.review.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.review.API.Users

@Entity(tableName = "User")
data class User(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val ID: Int,
           @ColumnInfo(name = "Name") val name: String,
           @ColumnInfo(name = "Location") val location: String) {
    companion object{
        val words = arrayListOf<Users.UserDetails>()
        val fav = arrayListOf<Users.UserDetails>()
    }
}