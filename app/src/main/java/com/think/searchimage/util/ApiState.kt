package com.think.searchimage.util

import com.think.searchimage.model.FeaturedImages

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data:FeaturedImages.Photos.Photo) : ApiState()
    object Empty : ApiState()
}