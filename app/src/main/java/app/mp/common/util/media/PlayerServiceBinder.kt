package app.mp.common.util.media

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import app.mp.model.service.AudioPlayerService

class PlayerServiceBinder : ServiceConnection {
    var isBound = false
    lateinit var service: AudioPlayerService

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as AudioPlayerService.LocalBinder
        this.service = binder.service
        isBound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
    }

}