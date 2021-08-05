package com.github.youtube_searcher.model.fullresponse

import com.google.gson.annotations.SerializedName


data class YoutubeSearchResponse(
    @SerializedName("etag")
    val etag: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("kind")
    val kind: String,
    @SerializedName("nextPageToken")
    val nextPageToken: String?,
    @SerializedName("pageInfo")
    val pageInfo: PageInfo,
    @SerializedName("prevPageToken")
    val prevPageToken: String?,
    @SerializedName("regionCode")
    val regionCode: String,
    @SerializedName("error")
    val error: Error
)