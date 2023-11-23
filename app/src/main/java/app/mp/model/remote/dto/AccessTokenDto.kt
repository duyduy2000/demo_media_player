package app.mp.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("scope") val scope: String,
    @SerialName("expires_in") val expriesIn: Int,
    @SerialName("refresh_token") val refreshToken: String
)