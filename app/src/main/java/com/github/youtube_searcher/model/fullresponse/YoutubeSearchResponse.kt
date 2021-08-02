package com.github.youtube_searcher.model.fullresponse


data class YoutubeSearchResponse(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String?,
    val pageInfo: PageInfo,
    val prevPageToken: String?,
    val regionCode: String
)