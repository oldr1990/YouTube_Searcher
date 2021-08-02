package com.github.youtube_searcher.model.fullresponse


import com.google.gson.annotations.SerializedName

data class Id(
    val kind: String,
    val videoId: String
)