package app.mp.model.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("PLUGIN_IS_NOT_ENABLED")
@Serializable
data class AudioListDto(
    @SerialName("count") val count: Int,
    @SerialName("previous") val previous: String?,
    @SerialName("next") val next: String?,
    @SerialName("results") val audioList: List<Audio>
) {
    @Serializable
    data class Audio(
        @SerialName("id") val id: Int,
        @SerialName("url") val url: String,
        @SerialName("name") val name: String,
        @SerialName("tags") val tags: List<String>,
        @SerialName("description") val description: String,
        @SerialName("created") val created: String,
        @SerialName("type") val type: String,
        @SerialName("filesize") val filesize: Int,
        @SerialName("duration") val duration: Double,
        @SerialName("username") val username: String,
        @SerialName("previews") val previews: Previews,
        @SerialName("num_downloads") val numDownloads: Int,
        @SerialName("avg_rating") val avgRating: Double,
        @SerialName("distance_to_target") val distanceToTarget: Double
    ) {
        @Serializable
        data class Previews(
            @SerialName("preview-hq-mp3") val previewHqMp3: String,
            @SerialName("preview-hq-ogg") val previewHqOgg: String,
            @SerialName("preview-lq-mp3") val previewLqMp3: String,
            @SerialName("preview-lq-ogg") val previewLqOgg: String
        )
    }

    companion object {
        const val infoFields =
            "id,url,name,tags,description,created,type,filesize,duration,username,previews,num_downloads,avg_rating"
    }
}