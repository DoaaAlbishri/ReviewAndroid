package com.example.review.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.review.R
import com.example.review.dataBase.User
import com.example.review.fragment_fav
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewFavoriteAdapter(private val favoriteUser: fragment_fav, private val fav: List<User>) : RecyclerView.Adapter<RecyclerViewFavoriteAdapter.ItemViewHolder>(){
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
        val word=fav[position]

        holder.itemView.apply{
            favBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
            tv.text= "${word.ID} \n ${word.name} \n ${word.location} "
            favBtn.setOnClickListener {
                favoriteUser.delete(word.ID,word.name,word.location)
            }
        }

    }

    override fun getItemCount(): Int =fav.size

}