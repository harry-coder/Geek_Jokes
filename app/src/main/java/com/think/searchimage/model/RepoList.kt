package com.think.searchimage.model

import com.google.gson.annotations.SerializedName

data class RepoList(

    @SerializedName("total_count")
    val total: Int = 0,

    @SerializedName("items")
    val items: List<Repo> = emptyList(),

    val nextPage: Int? = null
)