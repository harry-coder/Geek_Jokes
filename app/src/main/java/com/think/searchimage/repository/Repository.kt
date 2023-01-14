package com.think.searchimage.repository

import com.think.searchimage.model.RepoList
import com.think.searchimage.requestinterface.ApiInterface
import com.think.searchimage.requestinterface.GenericResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val TAG = "Repository"
class Repository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getTrendingRepos():GenericResponse<RepoList> = apiInterface.getTrendingRepos("kotlin",1,20)
    suspend fun getJokesFromServer():GenericResponse<String> = apiInterface.getJokes();
}