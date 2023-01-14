package com.think.searchimage

import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.think.searchimage.adapters.JokesAdapter
import com.think.searchimage.adapters.MediaAdapter
import com.think.searchimage.databinding.ActivityMainBinding
import com.think.searchimage.extentions.showToastLong
import com.think.searchimage.model.Joke
import com.think.searchimage.viewmodel.JokesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: JokesViewModel by viewModels()

    private lateinit var jokesAdapter:JokesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.root.also {
            setContentView(it)
        }

        setJokesAdapter()
        getTrendingJokes()
        registerFailureLiveData()


    }

    private fun registerFailureLiveData(){
        viewModel.failureResponseLiveData.observe(this){
            it.getData()?.let {
                showToastLong(it.message)
            }
        }
    }

    private fun setJokesAdapter(){
        binding.rvJokes.apply {
            jokesAdapter=JokesAdapter()
            adapter= jokesAdapter
            setHasFixedSize(true)

        }
    }
    private fun getTrendingJokes(){

        viewModel._jokesLiveData.observe(this){
            if(it.getData()!=null){

                val dataList=it.peekContent() as ArrayList<Joke>
                jokesAdapter.submitList(it.peekContent() as ArrayList<Joke>)
                binding.rvJokes.smoothScrollToPosition(dataList.size-1)
            }
        }
    }



}