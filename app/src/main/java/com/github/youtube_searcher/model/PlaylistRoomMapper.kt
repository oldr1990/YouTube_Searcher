package com.github.youtube_searcher.model

import com.github.youtube_searcher.util.EntityMapper
import javax.inject.Inject

class PlaylistRoomMapper @Inject constructor() : EntityMapper<RoomItem, MappedYoutubeItem> {
    override fun mapFromEntity(entity: RoomItem): MappedYoutubeItem =
        MappedYoutubeItem(
            entity.title,
            entity.description,
            entity.imageUrl
        )

    override fun mapToEntity(domainModel: MappedYoutubeItem): RoomItem =
        RoomItem(
            domainModel.title,
            domainModel.description,
            domainModel.imageUrl
        )
    fun mapListToRoomItem(listToMap: List<MappedYoutubeItem>):List<RoomItem> =
    listToMap.map {item -> mapToEntity(item) }
}