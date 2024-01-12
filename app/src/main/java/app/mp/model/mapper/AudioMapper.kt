package app.mp.model.mapper

import app.mp.model.model.Audio
import app.mp.model.remote.dto.AudioListDto

//fun AudioDto.toModel() = Audio(
//    id = id,
//    url = url,
//    name = name,
//    tags = tags,
//    description = description,
//    type = type,
//    duration = duration,
//    created = created,
//    username = username,
//    downloadNumber = numDownloads,
//    averageRating = avgRating,
//    previewHqMp3 = previews.previewHqMp3,
//)

fun AudioListDto.toModel() = audioList.map {
    Audio(
        id = it.id.toLong(),
        name = it.name,
        duration = it.duration,
        author = it.username,
        fileSize = it.filesize,
        uri = it.previews.previewHqMp3,
    )
}
