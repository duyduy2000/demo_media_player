package app.mp.model.model

data class Track(
    val id: Int,
    val url: String,
    val name: String,
    val tags: List<String>,
    val description: String,
    val type: String,
    val duration: Double,
    val created: String,
    val username: String,
    val downloadNumber: Int,
    val averageRating: Double,
    val previewHqMp3: String,
)