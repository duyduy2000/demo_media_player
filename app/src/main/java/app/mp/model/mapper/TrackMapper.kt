package app.mp.model.mapper

import app.mp.model.remote.dto.TrackDto
import app.mp.model.model.Track

object TrackMapper {

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
}