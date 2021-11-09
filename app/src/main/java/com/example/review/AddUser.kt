package com.example.review

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class AddUser : AppCompatActivity() {
    lateinit var edName :EditText
    lateinit var edLoc :EditText
    lateinit var btsave :Button
    lateinit var btview :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        edName = findViewById(R.id.edName)
        edLoc = findViewById(R.id.edLoc)
        btsave = findViewById(R.id.btsave)
        btview = findViewById(R.id.btview)

        btsave.setOnClickListener {
            if(edLoc.text.isNotEmpty()&&edName.text.isNotEmpty()) {
                var user = User.UserDetails(0,edName.text.toString(), edLoc.text.toString())
                addUserdata(user, onResult = {
                    edName.setText("")
                    edLoc.setText("")
                })
            }else{
                Toast.makeText(this, "fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
        btview.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun addUserdata(user: User.UserDetails, onResult: () -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        if (apiInterface != null) {
            apiInterface.addUser(user).enqueue(object : Callback<User.UserDetails> {
                override fun onResponse(
                    call: Call<User.UserDetails>,
                    response: Response<User.UserDetails>
                ) {
                    onResult()
                    Toast.makeText(applicationContext, "Save Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<User.UserDetails>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }
}