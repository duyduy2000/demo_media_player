package app.mp.model.mapper

import app.mp.model.model.Track
import app.mp.model.remote.dto.TrackDto
import app.mp.model.remote.dto.TrackListDto

fun TrackDto.toModel() = Track(
    id = id,
    url = url,
    name = name,
    tags = tags,
    description = description,
    type = type,
    duration = duration,
    created = created,
    username = username,
    downloadNumber = numDownloads,
    averageRating = avgRating,
    previewHqMp3 = previews.previewHqMp3,
)

fun TrackListDto.toModel() = trackList.map {
    Track(
        id = it.id,
        url = it.url,
        name = it.name,
        tags = it.tags,
        description = it.description,
        type = it.type,
        duration = it.duration,
        created = it.created,
        username = it.username,
        downloadNumber = it.numDownloads,
        averageRating = it.avgRating,
        previewHqMp3 = it.previews.previewHqMp3,
    )
}
