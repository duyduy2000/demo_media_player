package app.mp.model.model

data class Audio(
    val id: Long,
    val name: String,
    val author: String,
    val duration: Double,
    val fileSize: Int,
    val uri: String,
) {
    override fun toString(): String {
        return "Track (\n" +
                "id = $id,\n" +
                "name = \"$name\",\n" +
                "duration = $duration,\n" +
                "author = \"$author\",\n" +
                "uri = $uri,\n" +
                ")\n"
    }
}