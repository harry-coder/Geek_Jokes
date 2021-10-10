package com.think.searchimage.requestinterface

import com.think.searchimage.model.FeaturedImages
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("services/rest/")
    fun getSearchedImages(@Query("text") text:String,
                          @Query("page") page:Int,
                          @Query("method") method:String,
                          @Query("api_key") key:String,
                          @Query("format") format:String,
                          @Query("nojsoncallback") callback:Int,
                          @Query("per_page") value:Int): Call<FeaturedImages>

}