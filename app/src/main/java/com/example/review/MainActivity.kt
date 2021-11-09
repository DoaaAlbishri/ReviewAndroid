package com.example.review

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var btUpDe: Button
    lateinit var btAdd: Button
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btUpDe = findViewById(R.id.btUpDe)
        btAdd = findViewById(R.id.btAdd)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = RecyclerViewAdapter(Users.words)
        recyclerView.layoutManager = LinearLayoutManager(this)
        CoroutineScope(IO).launch {
            getUser()
        }

        btUpDe.setOnClickListener {
            val intent = Intent(this,UpdateDelete::class.java)
            startActivity(intent)
        }
        btAdd.setOnClickListener {
            val intent = Intent(this,AddUser::class.java)
            startActivity(intent)
        }
    }
    private suspend fun getUser(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<List<User.UserDetails>> = apiInterface!!.getUser()

        call?.enqueue(object : Callback<List<User.UserDetails>> {
            override fun onResponse(
                    call: Call<List<User.UserDetails>>,
                    response: Response<List<User.UserDetails>>
            )
            {
                val resource: List<User.UserDetails>? = response.body()
                for(User in resource!!){
                    Users.words.add(User)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<User.UserDetails>>, t: Throwable) {
                Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_SHORT).show();
            }
        })
    }
}