package app.mp.model.model

import android.net.Uri

data class LocalAudio(
    val id: Long,
    val name: String,
    val author: String,
    val duration: Int,
    val fileSize: Int,
    val uri: Uri,
)