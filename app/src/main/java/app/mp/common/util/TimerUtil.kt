package app.mp.common.util

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.Runnable

object TimerUtil {

    fun runDelayedTask(delayMillis: Long, task: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable { task() },
            delayMillis,
        )
    }

}