package com.think.searchimage.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.think.searchimage.model.Event
import com.think.searchimage.model.FeaturedImages
import com.think.searchimage.network.newNetwork.FailureResponse
import com.think.searchimage.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewModel  @Inject constructor(private val repository: Repository):ViewModel() {

    var searchLiveData=MutableLiveData<String>()

    var imageLiveData=MediatorLiveData<Event<FeaturedImages?>>()
    private set

    var failureLiveData=MediatorLiveData<FailureResponse>()
        private set

    fun getImages(){
        imageLiveData.addSource(Transformations.switchMap(searchLiveData){
            it?.let {
                repository.getFeaturedImages(it,1)
            }
        }) {
            if (it.isSuccessful) {
                imageLiveData.value = Event(it.data)
            } else {
                failureLiveData.value = it.getFailureResponse()
            }

        }
    }

    fun loadMoreImages(page:Int){

        imageLiveData.addSource(repository.getFeaturedImages(searchLiveData.value?:"",page)) {
            if (it.isSuccessful) {
                imageLiveData.value = it.data?.let { it1 -> Event(it1) }
            } else {
                failureLiveData.value = it.getFailureResponse()
            }
        }

    }

}