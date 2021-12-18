package com.think.searchimage.viewmodel

import androidx.lifecycle.*
import com.think.searchimage.extentions.subscribeThem
import com.think.searchimage.model.*
import com.think.searchimage.network.newNetwork.FailureResponse
import com.think.searchimage.remote.Event
import com.think.searchimage.remote.NetworkResponse
import com.think.searchimage.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel  @Inject constructor(private val repository: Repository):BaseViewModel() {
    private var _repoMutableLiveData= MutableLiveData<Event<RepoList>>()
    val repoLiveData:LiveData<Event<RepoList>>
        get() = _repoMutableLiveData

    init {
        getTrendingRepos()
    }


    private fun getTrendingRepos(){
        viewModelScope.launch {
            when (val data=   repository.getTrendingRepos()) {
                is NetworkResponse.Success -> {
                    _repoMutableLiveData.value= data.body?.let { Event(it) }
                }
                else -> handleError(data)
            }
        }

    }


  /*  fun getDemoData(){
        viewModelScope.launch {

            repository.getJsonDemoData()
                .subscribeThem({

                },

                    {

                    },{

                    },{

                    })


        }

    }*/



}