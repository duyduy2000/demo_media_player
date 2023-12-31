package app.mp.common.util.media

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import app.mp.model.service.AudioPlayerService

class PlayerServiceBinder : ServiceConnection {
    var isBound = false
        private set
    lateinit var service: AudioPlayerService
        private set

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as AudioPlayerService.LocalBinder
        this.service = binder.service
        isBound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
    }

    fun bindServiceTo(context: Context) {
        context.apply {
            val intent = AudioPlayerService.getActionIntent(
                this,
                AudioPlayerService.Action.START,
            )
            bindService(
                intent,
                this@PlayerServiceBinder,
                Context.BIND_AUTO_CREATE,
            )
        }
    }

    fun unbindServiceFrom(context: Context) {
        context.apply {
            unbindService(this@PlayerServiceBinder)
            isBound = false
        }
    }

}