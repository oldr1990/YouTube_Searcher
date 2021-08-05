package com.github.youtube_searcher.model.fullresponse.error

import com.google.gson.annotations.SerializedName


data class ErrorX(
    @SerializedName("domain")
    val domain: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("reason")
    val reason: String
)