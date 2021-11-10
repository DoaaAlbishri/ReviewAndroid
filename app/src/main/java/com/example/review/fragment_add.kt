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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.review.API.APIClient
import com.example.review.API.APIInterface
import com.example.review.API.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class fragment_add : Fragment() {
    lateinit var edName : EditText
    lateinit var edLoc : EditText
    lateinit var btsave : Button
    lateinit var btview : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        edName = view.findViewById(R.id.edName)
        edLoc = view.findViewById(R.id.edLoc)
        btsave = view.findViewById(R.id.btsave)
        btview = view.findViewById(R.id.btview)

        btsave.setOnClickListener {
            if(edLoc.text.isNotEmpty()&&edName.text.isNotEmpty()) {
                var user = Users.UserDetails(0,edName.text.toString(), edLoc.text.toString())
                addUserdata(user, onResult = {
                    edName.setText("")
                    edLoc.setText("")
                })
            }else{
                Toast.makeText(requireContext(), "fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
        btview.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_add_to_fragment_main)
        }
        return view
    }
    private fun addUserdata(user: Users.UserDetails, onResult: () -> Unit) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        if (apiInterface != null) {
            apiInterface.addUser(user).enqueue(object : Callback<Users.UserDetails> {
                override fun onResponse(
                    call: Call<Users.UserDetails>,
                    response: Response<Users.UserDetails>
                ) {
                    onResult()
                    Toast.makeText(requireContext(), "Save Success!", Toast.LENGTH_SHORT).show();
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
}