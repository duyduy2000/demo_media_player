package app.mp.model.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName("access_token") val accessToken: String,
    @SerialName("scope") val scope: String,
    @SerialName("expires_in") val expriesIn: Int,
    @SerialName("refresh_token") val refreshToken: String
)