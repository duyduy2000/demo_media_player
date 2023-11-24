package app.mp.common.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object PermissionHandler {

    fun checkForegroundServicePermission(context: Context): Boolean {
        val foregroundServicePermission =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
                    && checkPermission(context, Manifest.permission.FOREGROUND_SERVICE))
                    || Build.VERSION.SDK_INT < Build.VERSION_CODES.P

        val postNotificationPermission =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                    && checkPermission(context, Manifest.permission.POST_NOTIFICATIONS))
                    || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU

        return foregroundServicePermission && postNotificationPermission
    }

    private fun checkPermission(context: Context, permission: String) =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
}