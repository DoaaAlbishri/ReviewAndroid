package com.example.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.review.adapter.RecyclerViewFavoriteAdapter
import com.example.review.dataBase.User


class fragment_fav : Fragment() {
    lateinit var btBack: Button
    lateinit var rvFav: RecyclerView
    private val myViewModel by lazy { ViewModelProvider(this).get(MyViewModel::class.java)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fav, container, false)
        btBack=view.findViewById(R.id.btBack)
        rvFav=view.findViewById(R.id.rvFav)
        val sharedPreferences = requireActivity().getSharedPreferences("sp",Context.MODE_PRIVATE)
        // retrieve id from sharedPreferences
        val wordID = sharedPreferences.getInt("wordID", 0)
        val wordName = sharedPreferences.getString("wordName","")
        val wordLoc = sharedPreferences.getString("wordLoc","")
        val ids = listOf<User>()
        // -1 not found , -2 come from main activity so not added
        if(wordID!=-1 && wordID!=-2)
            myViewModel.addUser(wordID, wordName!!,wordLoc!!)
        else if(wordID==-1)
            Toast.makeText(requireContext(), "didn't add successfully", Toast.LENGTH_SHORT).show()
        btBack.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_fav_to_fragment_main)
        }

        myViewModel.getUser().observe(viewLifecycleOwner, {
                users ->
            rvFav.adapter = RecyclerViewFavoriteAdapter(this,users)
            rvFav.layoutManager = LinearLayoutManager(requireContext())
        })
        return view
    }
    fun delete(ID:Int,name:String,location:String){
        val del =User(ID, name, location)
        myViewModel.deleteUser(ID,name,location)
        // val index = User.fav.indexOf(del)
        // User.fav.removeAt(index)
    }
}