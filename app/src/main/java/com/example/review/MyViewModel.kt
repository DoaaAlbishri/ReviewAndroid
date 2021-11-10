package com.example.review

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.review.dataBase.User
import com.example.review.dataBase.UserDatabase
import com.example.review.dataBase.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel (applicationContext : Application): AndroidViewModel(applicationContext)  {
    val userDao = UserDatabase.getInstance(applicationContext).UserDao()
    private val repository: UserRepository  = UserRepository(userDao)
    private val users: LiveData<List<User>> = repository.getUsers

    fun getUser(): LiveData<List<User>>{
        return users
    }

    fun addUser(id:Int, name: String,location:String){
        CoroutineScope(Dispatchers.IO).launch {
            val n = User(id, name,location)
            repository.addUser(n)
            println("added")
        }
    }

    fun deleteUser(ID: Int, name: String,location:String){
        CoroutineScope(Dispatchers.IO).launch {
            val del = User(ID,name,location)
            repository.deleteUser(del)
        }
    }
}