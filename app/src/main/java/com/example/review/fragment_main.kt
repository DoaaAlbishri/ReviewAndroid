package com.example.review

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.review.API.APIClient
import com.example.review.API.APIInterface
import com.example.review.API.Users
import com.example.review.adapter.RecyclerViewAdapter
import com.example.review.dataBase.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class fragment_main : Fragment() {
    lateinit var btUpDe: Button
    lateinit var btAdd: Button
    lateinit var btFav: Button
    lateinit var recyclerView: RecyclerView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("sp", Context.MODE_PRIVATE)
        btUpDe = view.findViewById(R.id.btUpDe)
        btAdd = view.findViewById(R.id.btAdd)
        btFav = view.findViewById(R.id.btFav)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = RecyclerViewAdapter(this,User.words)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        GlobalScope.launch {
            getUser()
        }
        btFav.setOnClickListener {
            // add id to shared preferences
            with(sharedPreferences.edit()) {
                putInt("wordID", -2)
                apply()
            }
            findNavController().navigate(R.id.action_fragment_main_to_fragment_fav)
        }
        btUpDe.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_fragment_upde)
        }
        btAdd.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_main_to_fragment_add)
        }

        return view
    }
    private suspend fun getUser(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<List<Users.UserDetails>> = apiInterface!!.getUser()

        call?.enqueue(object : Callback<List<Users.UserDetails>> {
            override fun onResponse(
                call: Call<List<Users.UserDetails>>,
                response: Response<List<Users.UserDetails>>
            )
            {
                val resource: List<Users.UserDetails>? = response.body()
                for(User in resource!!){
                    com.example.review.dataBase.User.words.add(User)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<Users.UserDetails>>, t: Throwable) {
                Toast.makeText(requireContext(), ""+t.message, Toast.LENGTH_SHORT).show();
            }
        })
    }
}