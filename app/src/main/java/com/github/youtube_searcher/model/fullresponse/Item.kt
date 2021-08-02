package com.github.youtube_searcher.model.fullresponse


data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)