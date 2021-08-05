package com.github.youtube_searcher.model.fullresponse.error

import com.google.gson.annotations.SerializedName


data class Error(
    @SerializedName("code")
    val code: Int,
    @SerializedName("errors")
    val errors: List<ErrorX>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)