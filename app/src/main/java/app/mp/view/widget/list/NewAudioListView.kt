package app.mp.view.widget.list

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
import app.mp.viewmodel.audio.AudioViewModel

class NewAudioListView(private val viewModel: AudioViewModel) {

    @Composable
    fun Build() {
        val audioList by viewModel.audioList.observeAsState()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            audioList?.let { list ->
                this.itemsIndexed(items = list) { index, item ->
                    AudioListItemView(item, isPlaying = false)

                    if (index != list.size - 1) Divider(
                        modifier = Modifier.alpha(0.2f),
                        thickness = 2.dp
                    )
                }
            }
        }
    }
}