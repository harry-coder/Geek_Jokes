package com.think.searchimage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.think.searchimage.adapters.MediaAdapter
import com.think.searchimage.constants.OnItemClickListener
import com.think.searchimage.constants.OnNoResultFoundListener
import com.think.searchimage.databinding.ActivityMainBinding
import com.think.searchimage.extentions.showToastLong
import com.think.searchimage.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.shimmer_layout.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MediaAdapter
    private val viewModel: ImageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            lifecycleOwner = this@MainActivity
            executePendingBindings()

        }
        startShimmer()
        setObservers()
        setRecyclerView()
        setListeners()
    }

    private fun startShimmer(){
        binding.includeShimmer.userShimmer.visibility=View.VISIBLE
        binding.includeShimmer.userShimmer.startShimmerAnimation()
    }
    private fun stopShimmer(){
        binding.includeShimmer.userShimmer.visibility=View.GONE
        binding.includeShimmer.userShimmer.stopShimmerAnimation()
    }
    private fun setObservers() {
        viewModel.repoLiveData.observe(this) {
            if (it != null) {
                if (it.peekContent() != null) {
                    stopShimmer()
                    adapter.setSource(it.peekContent().items)

                }
            }
        }
        viewModel.failureResponseLiveData.observe(this){
            showToastLong(it.peekContent().message)
        }
    }


    private fun setListeners() {
        binding.searchview.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }


        })
    }


    private fun setRecyclerView() {
        layoutManager = LinearLayoutManager(this)

        binding.rvMedia.layoutManager = layoutManager
        adapter = MediaAdapter(this,object:OnItemClickListener{
            override fun onItemClicked(position: Int) {
                val item= adapter.filteredList[position]
                item.isSelected=!(item.isSelected)
                adapter.notifyItemChanged(position)
            }

        },object :OnNoResultFoundListener{
            override fun onNoResultFound(size: Int) {
                if(size==0)
                Toast.makeText(this@MainActivity,getString(R.string.no_repo),Toast.LENGTH_SHORT).show()
            }

        })
        adapter.hasStableIds()
        binding.rvMedia.adapter = adapter
    }



}