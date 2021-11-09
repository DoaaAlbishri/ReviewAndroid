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

class UpdateDelete : AppCompatActivity() {
    lateinit var edId : EditText
    lateinit var edName : EditText
    lateinit var edLoc : EditText
    lateinit var btdelete : Button
    lateinit var btupdate : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)
        edId = findViewById(R.id.edId)
        edName = findViewById(R.id.edName)
        edLoc = findViewById(R.id.edLoc)
        btdelete = findViewById(R.id.btdelete)
        btupdate = findViewById(R.id.btupdate)
        btdelete.setOnClickListener {
            if (edId.text.isNotEmpty()) {
                var user = User.UserDetails(
                    edId.text.toString().toInt(),
                    edName.text.toString(),
                    edLoc.text.toString()
                )
                deleteUserdata(user, onResult = {
                    edId.setText("")
                    edName.setText("")
                    edLoc.setText("")
                })
            } else {
                Toast.makeText(this, "Enter ID please", Toast.LENGTH_SHORT).show()
            }
        }
        btupdate.setOnClickListener {
            if (edId.text.isNotEmpty() && edName.text.isNotEmpty() && edLoc.text.isNotEmpty()) {
                var user = User.UserDetails(
                    edId.text.toString().toInt(),
                    edName.text.toString(),
                    edLoc.text.toString()
                )
                updateUserdata(user, onResult = {
                    edId.setText("")
                    edName.setText("")
                    edLoc.setText("")
                })
            } else {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateUserdata(user: User.UserDetails, onResult: () -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.updateUser(user.pk!!,user).enqueue(object : Callback<User.UserDetails> {
                override fun onResponse(
                    call: Call<User.UserDetails>,
                    response: Response<User.UserDetails>
                ) {
                    onResult()
                    Toast.makeText(applicationContext, "Update Success!", Toast.LENGTH_SHORT).show();
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

    private fun deleteUserdata(user: User.UserDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.deleteUser(user.pk!!).enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    onResult()
                    Toast.makeText(applicationContext, "Delete Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}