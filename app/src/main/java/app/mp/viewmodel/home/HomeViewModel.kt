package app.mp.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.mp.common.util.ResponseResult
import app.mp.common.util.media.AudioPlayerState
import app.mp.model.mapper.toModel
import app.mp.model.repo.def.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trackRepository: TrackRepository,
    audioPlayerState: AudioPlayerState,
) : ViewModel() {

    data class UiState(
        val nothing: Boolean = true
    )

    var uiState = UiState()
        private set

    val playerState = audioPlayerState.playerState.asLiveData()

    fun getTrack() {
        viewModelScope.launch {
            trackRepository.getTrackFromId(680316).collect {
                when (it) {
                    is ResponseResult.Failed -> uiState
                    is ResponseResult.Success -> it.data!!.toModel()
                    is ResponseResult.Unknown -> uiState
                }
            }
        }
    }


}