package app.mp.view.widget.list

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import app.mp.common.di.AppDependencies
import app.mp.viewmodel.audio.AudioViewModel
import dagger.hilt.android.EntryPointAccessors

class AudioListView(private val viewModel: AudioViewModel, context: Context) {

    private val serviceBinder =
        EntryPointAccessors.fromApplication(context, AppDependencies::class.java).serviceBinder()

    @Composable
    fun Build() {
        val audioList by viewModel.audioList.observeAsState()
        val playingAudio by viewModel.currentTrackState.observeAsState()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            audioList?.let { list ->
                this.itemsIndexed(items = list) { index, audio ->
                    AudioListItemView(
                        item = audio,
                        isPlaying = audio.id.toString() == playingAudio?.id && audio.name == playingAudio?.name && audio.author == playingAudio?.author,
                        modifier = Modifier.clickable { changeAudio(index) }
                    )

                    if (index != list.size - 1) Divider(
                        modifier = Modifier.alpha(0.2f),
                        thickness = 2.dp
                    )
                }
            }
        }
    }

    private fun changeAudio(index: Int) = serviceBinder.usePlayer {
        viewModel.audioList.value?.let { addAudios(it) }
        playAudioByIndex(index)
    }
}