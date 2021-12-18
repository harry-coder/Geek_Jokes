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
import com.think.searchimage.model.Repo
import java.util.*


class MediaAdapter(
    var context: Context,val onClickListener: OnItemClickListener,val noResultFound:OnNoResultFoundListener
) : RecyclerView.Adapter<MediaAdapter.ItemViewHolder>(),Filterable {

    var fullList: ArrayList<Repo> = arrayListOf()
    var filteredList = ArrayList<Repo>()


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
        viewHolder.bind(filteredList[position])



    override fun getItemCount(): Int =
        if (filteredList.isNotEmpty()) filteredList.size
        else 0


    fun setSource(repoList: List<Repo>) {
        filteredList.addAll(repoList)
        fullList.addAll(repoList)
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.setVariable(BR.repo, repo)
            binding.root.setOnClickListener {
                onClickListener.onItemClicked(absoluteAdapterPosition)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterList = arrayListOf<Repo?>()
                if (constraint.isNullOrEmpty()) {
                    filterList.addAll(fullList)
                } else {
                    for (row in fullList) {
                        if (row.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            println("Name ${row.name}")
                            filterList.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val list = results?.values as MutableList<Repo>
                filteredList.clear()
                filteredList.addAll(list)
                notifyDataSetChanged()
                noResultFound.onNoResultFound(filteredList.size)
            }
        }
    }
}












