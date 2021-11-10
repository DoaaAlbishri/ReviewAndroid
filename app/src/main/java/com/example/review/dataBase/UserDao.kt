package com.example.review.dataBase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.review.dataBase.User

@Dao
interface UserDao {

    @androidx.room.Query("SELECT * FROM User")
    fun getUser(): LiveData<List<User>>

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Update
    fun updateUser(user: User)

}