package app.mp.common.util.media

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import androidx.fragment.app.Fragment
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

    fun bindServiceTo(fragment: Fragment) {
        fragment.apply {
            val intent = AudioPlayerService.getActionIntent(
                requireContext(),
                AudioPlayerService.Action.START,
            )
            requireActivity().bindService(
                intent,
                this@PlayerServiceBinder,
                Context.BIND_AUTO_CREATE,
            )
        }
    }

    fun unbindServiceFrom(fragment: Fragment) {
        fragment.apply {
            requireContext().unbindService(this@PlayerServiceBinder)
            isBound = false
        }
    }

}