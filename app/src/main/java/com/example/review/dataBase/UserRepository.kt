package com.example.review.dataBase

import androidx.lifecycle.LiveData
import com.example.review.dataBase.User
import com.example.review.dataBase.UserDao

class UserRepository(private val userDao: UserDao) {

    val getUsers: LiveData<List<User>> = userDao.getUser()

    suspend fun addUser(user: User){
        userDao.insertUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }
}