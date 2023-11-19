package app.mp.model.model

data class AccessToken(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
)
