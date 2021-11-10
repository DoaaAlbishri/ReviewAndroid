package com.example.review.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class UserDatabase:RoomDatabase() {

    companion object{
        var instance: UserDatabase?=null;
        fun getInstance(ctx: Context): UserDatabase
        {
            if(instance !=null)
            {
                return  instance as UserDatabase;
            }
            instance = Room.databaseBuilder(ctx, UserDatabase::class.java,"details").run { allowMainThreadQueries() }.build();
            return instance as UserDatabase;
        }
    }
    abstract fun UserDao(): UserDao;
}