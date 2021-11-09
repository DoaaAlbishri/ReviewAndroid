package com.example.review

import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("/test/")
    fun getUser(): Call<List<User.UserDetails>>

    @POST("/test/")
    fun addUser(@Body userData: User.UserDetails): Call<User.UserDetails>

    @PUT("/test/{id}")
    fun updateUser(@Path("id") id : Int , @Body userData: User.UserDetails): Call<User.UserDetails>

    @DELETE("/test/{id}")
    fun deleteUser(@Path("id") id : Int ): Call<Void>

    @GET("/test/{id}")
    fun getUserId(@Path("id") id : Int): Call<User.UserDetails>
}