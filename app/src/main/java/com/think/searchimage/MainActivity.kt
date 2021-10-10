package com.think.searchimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.think.searchimage.adapters.MediaAdapter
import com.think.searchimage.databinding.ActivityMainBinding
import com.think.searchimage.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast




@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager:GridLayoutManager
    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:MediaAdapter
    private val viewModel: ImageViewModel by viewModels()
    private var page=1
    private var loading=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      binding=DataBindingUtil.setContentView(this,R.layout.activity_main)


        setObservers()
        setRecyclerView()

        setListeners()
        viewModel.getImages()


    }

    private fun setObservers() {
        viewModel.imageLiveData.observe(this){
            if(it!=null) {
                if (it.getData() != null) {
                    if (page == 1) adapter.list.clear()
                    adapter.setSource(it.peekContent()!!.photos.photo)
                    binding.pbStatus.visibility = View.GONE

                } else {
                    binding.pbStatus.visibility = View.GONE
                    Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT)
                }
            }
            else {
                binding.pbStatus.visibility = View.GONE
                Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT)
            }
        }

    }

    private fun setListeners(){
        binding.etSearch.doAfterTextChanged {

            val searchText=it.toString()
            if(searchText.isNotBlank() && searchText.isNotEmpty()){
                if(!binding.pbStatus.isShown) binding.pbStatus.visibility=View.VISIBLE
                page=1
                viewModel.searchLiveData.value=searchText

            }
            else viewModel.searchLiveData.value=null


        }

    }


    private fun setRecyclerView() {
        layoutManager = GridLayoutManager(this, 3)

        binding.rvMedia.layoutManager = layoutManager
        adapter = MediaAdapter(this )
        binding.rvMedia.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    //val last = layoutManager.findLastVisibleItemPosition()

                    val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition()+1
                    if (visibleItemCount == layoutManager.itemCount){
                        page=viewModel.imageLiveData.value?.peekContent()?.photos?.page?:1
                        page++
                        viewModel.loadMoreImages(page = page)
                        binding.pbStatus.visibility=View.VISIBLE
                    }

                }
            }
        })
        adapter.hasStableIds()
        binding.rvMedia.adapter = adapter
    }


}