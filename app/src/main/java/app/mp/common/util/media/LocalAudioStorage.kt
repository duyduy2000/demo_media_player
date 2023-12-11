package app.mp.common.util.media

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import app.mp.common.util.PermissionHandler
import app.mp.model.model.LocalTrack
import kotlinx.coroutines.flow.flow

class LocalAudioStorage(private val context: Context) {
    fun getAllLocalAudios() = flow {
        accessLocalAudioStorage {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(AudioStore._ID))
                val author =
                    if (it.getString(it.getColumnIndexOrThrow(AudioStore.ARTIST)) == "<unknow>") "Unknow Author"
                    else it.getString(it.getColumnIndexOrThrow(AudioStore.ARTIST))

                emit(
                    LocalTrack(
                        id = id,
                        name = it.getString(it.getColumnIndexOrThrow(AudioStore.DISPLAY_NAME)),
                        author = author,
                        duration = it.getInt(it.getColumnIndexOrThrow(AudioStore.DURATION)),
                        fileSize = it.getInt(it.getColumnIndexOrThrow(AudioStore.SIZE)),
                        uri = ContentUris.withAppendedId(
                            /* contentUri = */ AudioStore.EXTERNAL_CONTENT_URI,
                            /* id = */ id
                        ),
                    )
                )
            }
        }

    }

    private suspend fun accessLocalAudioStorage(onCursorMove: suspend (cursor: Cursor) -> Unit) {
        val projection = arrayOf(
            AudioStore._ID,
            AudioStore.DISPLAY_NAME,
            AudioStore.ARTIST,
            AudioStore.DURATION,
            AudioStore.SIZE
        )

        if (PermissionHandler.checkMediaStoragePermission(context)) {
            context.contentResolver.query(
                AudioStore.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null,
                null
            )?.use { onCursorMove(it) }
        }
    }


}

private typealias AudioStore = MediaStore.Video.Media