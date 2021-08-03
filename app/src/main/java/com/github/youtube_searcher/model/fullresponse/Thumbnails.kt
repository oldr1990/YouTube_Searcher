package com.github.youtube_searcher.model.fullresponse

import com.google.gson.annotations.SerializedName


data class Thumbnails(
    @SerializedName("default")
    val default: Default,
    @SerializedName("high")
    val high: High,
    @SerializedName("medium")
    val medium: Medium
)