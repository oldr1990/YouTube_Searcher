package com.github.youtube_searcher.model

import com.github.youtube_searcher.model.fullresponse.*
import com.github.youtube_searcher.util.EntityMapper
import javax.inject.Inject

class YoutubeResponseMapper @Inject constructor() : EntityMapper<Item, MappedYoutubeItem> {


    override fun mapFromEntity(entity: Item): MappedYoutubeItem =
        MappedYoutubeItem(
            entity.snippet.title,
            entity.snippet.description,
            entity.snippet.thumbnails.high.url
        )

    override fun mapToEntity(domainModel: MappedYoutubeItem): Item {
        return Item(
            "", Id("", ""), "", Snippet(
                "", "", "", "", "", "",
                Thumbnails(Default(0, "", 0), High(0, "", 0), Medium(0, "", 0)), "",
            )
        )
    }

    fun mapListFromEntity(entityList: List<Item>): List<MappedYoutubeItem> =
        entityList.map { item -> mapFromEntity(item) }
}