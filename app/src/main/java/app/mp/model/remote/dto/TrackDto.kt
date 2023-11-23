package app.mp.model.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDto(
    @SerialName("id") val id: Int,
    @SerialName("url") val url: String,
    @SerialName("name") val name: String,
    @SerialName("tags") val tags: List<String>,
    @SerialName("description") val description: String,
    @SerialName("geotag") val geotag: String?,
    @SerialName("created") val created: String,
    @SerialName("license") val license: String,
    @SerialName("type") val type: String,
    @SerialName("channels") val channels: Int,
    @SerialName("filesize") val filesize: Int,
    @SerialName("bitrate") val bitrate: Int,
    @SerialName("bitdepth") val bitdepth: Int,
    @SerialName("duration") val duration: Double,
    @SerialName("samplerate") val samplerate: Double,
    @SerialName("username") val username: String,
    @SerialName("pack") val pack: String,
    @SerialName("pack_name") val packName: String?,
    @SerialName("download") val download: String,
    @SerialName("bookmark") val bookmark: String,
    @SerialName("previews") val previews: Previews,
    @SerialName("images") val images: Images,
    @SerialName("num_downloads") val numDownloads: Int,
    @SerialName("avg_rating") val avgRating: Double,
    @SerialName("num_ratings") val numRatings: Int,
    @SerialName("rate") val rate: String,
    @SerialName("comments") val comments: String,
    @SerialName("num_comments") val numComments: Int,
    @SerialName("comment") val comment: String,
    @SerialName("similar_sounds") val similarSounds: String,
    @SerialName("analysis") val analysis: String,
    @SerialName("analysis_frames") val analysisFrames: String,
    @SerialName("analysis_stats") val analysisStats: String,
    @SerialName("is_explicit") val isExplicit: Boolean
) {
    @Serializable
    data class Previews(
        @SerialName("preview-hq-mp3") val previewHqMp3: String,
        @SerialName("preview-hq-ogg") val previewHqOgg: String,
        @SerialName("preview-lq-mp3") val previewLqMp3: String,
        @SerialName("preview-lq-ogg") val previewLqOgg: String
    )

    @Serializable
    data class Images(
        @SerialName("waveform_m") val waveformM: String,
        @SerialName("waveform_l") val waveformL: String,
        @SerialName("spectral_m") val spectralM: String,
        @SerialName("spectral_l") val spectralL: String,
        @SerialName("waveform_bw_m") val waveformBwM: String,
        @SerialName("waveform_bw_l") val waveformBwL: String,
        @SerialName("spectral_bw_m") val spectralBwM: String,
        @SerialName("spectral_bw_l") val spectralBwL: String
    )
}