package com.think.searchimage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.think.searchimage.database.DBHelper
import com.think.searchimage.database.entity.LocationEntity
import com.think.searchimage.model.RepoList
import com.think.searchimage.remote.Event
import com.think.searchimage.remote.NetworkResponse
import com.think.searchimage.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: Repository,
    private val dbHelper: DBHelper
) : BaseViewModel() {
    private var _repoMutableLiveData = MutableLiveData<Event<RepoList>>()
    val repoLiveData: LiveData<Event<RepoList>>
        get() = _repoMutableLiveData


    private fun getTrendingRepos() {
        viewModelScope.launch {
            when (val data = repository.getTrendingRepos()) {
                is NetworkResponse.Success -> {
                    _repoMutableLiveData.value = data.body?.let { Event(it) }
                }
                else -> handleError(data)
            }
        }

    }


    fun saveLocationData(userLocation: LocationEntity) = liveData<Event<Long?>> {
        withContext(Dispatchers.IO) {
            emit(Event(dbHelper.locationDao().insert(userLocation)))
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