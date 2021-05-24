package com.example.moviebuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieAdapter(private val listener: MovieClicked): RecyclerView.Adapter<MovieViewHolder>()  {

    private val items: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        val viewHolder = MovieViewHolder(viewItem)
        viewItem.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleView.text = currentItem.title
        Picasso.with(holder.itemView.context).load("https://image.tmdb.org/t/p/w500/" + currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(updated: ArrayList<Movie>) {
        items.clear()
        items.addAll(updated)
        notifyDataSetChanged()
    }

}

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
}

interface MovieClicked {
    fun onItemClicked(item: Movie)
}