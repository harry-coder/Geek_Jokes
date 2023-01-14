package com.think.searchimage.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.think.searchimage.R
import com.think.searchimage.model.Joke
import kotlinx.android.synthetic.main.item_media.view.*

class JokesAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Joke>() {

        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {

            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem==newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return JokesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_media,
                parent,
                false
            ),

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is JokesViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Joke>) {
        differ.submitList(list)
    }

    class JokesViewHolder
    constructor(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Joke) = with(itemView) {
            itemView.tv_joke.text=item.jokeName

        }
    }


}