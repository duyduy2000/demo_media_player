package app.mp.common.util.media

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import app.mp.common.util.PermissionHandler
import app.mp.model.model.Audio
import kotlinx.coroutines.flow.flow

class LocalAudioStorage(private val context: Context) {
    fun getAllLocalAudios() = flow {
        val projection = arrayOf(
            AudioStore._ID,
            AudioStore.DISPLAY_NAME,
            AudioStore.ARTIST,
            AudioStore.DURATION,
            AudioStore.SIZE,
        )
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            AudioStore.getContentUri(MediaStore.VOLUME_EXTERNAL)
        else
            AudioStore.EXTERNAL_CONTENT_URI

        if (PermissionHandler.checkMediaStoragePermission(context)) {
            context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                null,
                null
            )?.use {
                while (it.moveToNext()) emit(it.getAudio())
            }
        }
    }

    private fun Cursor.getAudio(): Audio {
        val id = this.getLong(this.getColumnIndexOrThrow(AudioStore._ID))
        val author =
            if (this.getString(this.getColumnIndexOrThrow(AudioStore.ARTIST)) == "<unknow>")
                "Unknow Author"
            else
                this.getString(this.getColumnIndexOrThrow(AudioStore.ARTIST))

        return Audio(
            id = id,
            name = getString(getColumnIndexOrThrow(AudioStore.DISPLAY_NAME)),
            author = author,
            duration = getInt(getColumnIndexOrThrow(AudioStore.DURATION)).toDouble(),
            fileSize = getInt(getColumnIndexOrThrow(AudioStore.SIZE)),
            uri = ContentUris.withAppendedId(
                /* contentUri = */ AudioStore.EXTERNAL_CONTENT_URI,
                /* id = */ id
            ).toString(),
        )
    }


}

private typealias AudioStore = MediaStore.Audio.Media