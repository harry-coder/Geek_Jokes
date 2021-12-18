package com.think.searchimage.requestinterface

import com.think.searchimage.model.*
import com.think.searchimage.remote.NetworkResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/repositories?sort=stars")
    suspend fun getTrendingRepos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GenericResponse<RepoList>

}
typealias GenericResponse<S> = NetworkResponse<S, BaseError>