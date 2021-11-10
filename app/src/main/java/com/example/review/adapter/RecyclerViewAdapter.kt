package com.example.review.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.review.*
import com.example.review.API.Users
import com.example.review.dataBase.User
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter(private val main: fragment_main, private val words: ArrayList<Users.UserDetails>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    class ItemViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView)
    var ctx :Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        ctx=parent.context
        return ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_row
                        ,parent
                        ,false
                )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val word=words[position]

        holder.itemView.apply{
            if(User.fav.contains(word))
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            else
                favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            tv.text= "${word.pk.toString()} \n ${word.name} \n ${word.location} "
            favBtn.setOnClickListener {
                if(!User.fav.contains(word)) {
                    favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                    User.fav.add(word)
                    with(main.sharedPreferences.edit()) {
                        putInt("wordID", word.pk!!)
                        putString("wordName",word.name)
                        putString("wordLoc",word.location)
                        apply()
                    }
                    main.findNavController().navigate(R.id.action_fragment_main_to_fragment_fav)
                } else {
                    Toast.makeText(ctx, "you added before", Toast.LENGTH_SHORT).show()
//                    val index = User.fav.indexOf(word)
//                    User.fav.removeAt(index)
//                    favBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//                    notifyDataSetChanged()
                }
            }
        }

    }

    override fun getItemCount(): Int =words.size

}