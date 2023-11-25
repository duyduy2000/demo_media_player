package app.mp.model.mapper

import app.mp.model.model.Track
import app.mp.model.remote.dto.TrackDto

fun TrackDto.toModel() = Track(
    id = id,
    url = url,
    name = name,
    tags = tags,
    description = description,
    type = type,
    duration = duration,
    previewHqMp3 = previews.previewHqMp3,
)
