package app.mp.common.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionHandler {
    fun askPermissionsWhenAppStarted(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission(
                activity,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            )
        }
        requestPermission(
            activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        )
    }

    fun checkForegroundServicePermission(context: Context): Boolean {
        val foregroundServicePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            checkPermission(context, Manifest.permission.FOREGROUND_SERVICE)
        else true

        val postNotificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(context, Manifest.permission.POST_NOTIFICATIONS)
        else true

        return foregroundServicePermission && postNotificationPermission
    }

    fun checkMediaStoragePermission(context: Context): Boolean {
        val readStoragePermission =
            checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)

        val readAudioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            checkPermission(context, Manifest.permission.READ_MEDIA_AUDIO)
        else true

        return readStoragePermission && readAudioPermission
    }

    private fun checkPermission(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(activity: Activity, permissions: Array<String>) {
        ActivityCompat.requestPermissions(
            /* activity = */ activity,
            /* permissions = */ permissions,
            /* requestCode = */ 0
        )
    }

}