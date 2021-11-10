package com.example.review

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.review.API.APIClient
import com.example.review.API.APIInterface
import com.example.review.API.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class fragment_upde : Fragment() {
    lateinit var edId : EditText
    lateinit var edName : EditText
    lateinit var edLoc : EditText
    lateinit var btdelete : Button
    lateinit var btupdate : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upde, container, false)
        edId = view.findViewById(R.id.edId)
        edName = view.findViewById(R.id.edName)
        edLoc = view.findViewById(R.id.edLoc)
        btdelete = view.findViewById(R.id.btdelete)
        btupdate = view.findViewById(R.id.btupdate)
        btdelete.setOnClickListener {
            if (edId.text.isNotEmpty()) {
                var user = Users.UserDetails(
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
                Toast.makeText(requireContext(), "Enter ID please", Toast.LENGTH_SHORT).show()
            }
        }
        btupdate.setOnClickListener {
            if (edId.text.isNotEmpty() && edName.text.isNotEmpty() && edLoc.text.isNotEmpty()) {
                var user = Users.UserDetails(
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
                Toast.makeText(requireContext(), "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
    private fun updateUserdata(user: Users.UserDetails, onResult: () -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.updateUser(user.pk!!,user).enqueue(object : Callback<Users.UserDetails> {
                override fun onResponse(
                    call: Call<Users.UserDetails>,
                    response: Response<Users.UserDetails>
                ) {
                    onResult()
                    Toast.makeText(requireContext(), "Update Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<Users.UserDetails>, t: Throwable) {
                    onResult()
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }

    private fun deleteUserdata(user: Users.UserDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.deleteUser(user.pk!!).enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    onResult()
                    Toast.makeText(requireContext(), "Delete Success!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult()
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }
}