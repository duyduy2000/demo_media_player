package app.mp.model.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName(value = "access_token")
    val accessToken: String,
    @SerialName(value = "token_type")
    val tokenType: String,
    @SerialName(value = "expires_in")
    val expiresIn: Int,
)
