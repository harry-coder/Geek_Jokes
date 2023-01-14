package com.think.searchimage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.think.searchimage.BR
import com.think.searchimage.R
import com.think.searchimage.constants.OnItemClickListener
import com.think.searchimage.constants.OnNoResultFoundListener
import com.think.searchimage.databinding.ItemMediaBinding
import com.think.searchimage.model.Joke
import com.think.searchimage.model.Repo
import java.util.*


class MediaAdapter(
    var context: Context
) : RecyclerView.Adapter<MediaAdapter.ItemViewHolder>() {

    var jokesList = ArrayList<Joke>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaAdapter.ItemViewHolder {

        return inflateRecentViews(parent)


    }

    private fun inflateRecentViews(parent: ViewGroup): MediaAdapter.ItemViewHolder {
        val binding: ItemMediaBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.item_media, parent, false
        )
        return ItemViewHolder(binding)

    }


    override fun onBindViewHolder(viewHolder: MediaAdapter.ItemViewHolder, position: Int) =
        viewHolder.bind(jokesList[position].jokeName)



    override fun getItemCount(): Int =
        if (jokesList.isNotEmpty()) jokesList.size
        else 0


    fun setDataLast(joke:ArrayList<Joke>){
        jokesList.addAll(joke)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(joke: String) {

            binding.tvJoke.text=joke

            //binding.setVariable(BR.repo, repo)

        }
    }


}












