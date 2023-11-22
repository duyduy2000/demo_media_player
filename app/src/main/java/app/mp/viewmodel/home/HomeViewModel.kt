package app.mp.viewmodel.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mp.common.util.ResponseResult
import app.mp.common.util.media.AudioPlayer
import app.mp.model.repo.def.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trackRepository: TrackRepository,
//    private val tokenRepository: TokenRepository,
) : ViewModel() {

    data class UiState(
        val nothing: Boolean = true
    )

    var uiState = UiState()
        private set

    fun getTrack(context: Context) {
        viewModelScope.launch {
            trackRepository.getTrackFromId(680316).collect {
                when (it) {
                    is ResponseResult.Failed -> uiState
                    is ResponseResult.Success -> AudioPlayer.playTrack(remoteUrl = it.data!!.previews.previewHqMp3, context)
                    is ResponseResult.Unknown -> uiState
                }
            }
        }
    }

}