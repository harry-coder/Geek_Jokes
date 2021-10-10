package com.think.searchimage.repository

import androidx.lifecycle.MutableLiveData
import com.think.searchimage.constants.Constants
import com.think.searchimage.model.FeaturedImages
import com.think.searchimage.network.newNetwork.NetworkCallback
import com.think.searchimage.network.newNetwork.Result
import com.think.searchimage.requestinterface.ApiInterface
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "Repository"
class Repository @Inject constructor(private val apiInterface: ApiInterface) {

    fun getFeaturedImages(text:String,page:Int):MutableLiveData<com.think.searchimage.network.newNetwork.Result<FeaturedImages>> {

        val liveData:MutableLiveData<Result<FeaturedImages>> = MutableLiveData()
        apiInterface.getSearchedImages(text,page,Constants.SEARCH_METHOD,Constants.API_KEY,"json",1,20).enqueue(object :
            NetworkCallback<FeaturedImages>(){
            override fun onResponse(result: com.think.searchimage.network.newNetwork.Result<FeaturedImages>) {
                liveData.value=result
            }

        })
        return liveData
    }
}