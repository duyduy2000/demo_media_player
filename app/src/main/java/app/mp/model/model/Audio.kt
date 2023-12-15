package app.mp.model.model

data class Audio(
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
) {
    override fun toString(): String {
        return "Track (\n" +
                "id = $id,\n" +
                "url = \"$url\",\n" +
                "name = \"$name\",\n" +
                "tags = \"$tags\",\n" +
                "description = \"$description\",\n" +
                "type = \"$type\",\n" +
                "duration = $duration,\n" +
                "created = \"$created\",\n" +
                "username = \"$username\",\n" +
                "downloadNumber = $downloadNumber,\n" +
                "averageRating = $averageRating,\n" +
                "previewHqMp3 = \"$previewHqMp3\",\n" +
                ")\n"
    }
}