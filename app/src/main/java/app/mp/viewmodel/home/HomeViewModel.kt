package app.mp.viewmodel.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.mp.common.util.media.AudioPlayerState
import app.mp.common.util.media.LocalAudioStorage
import app.mp.common.util.network.ResponseResult
import app.mp.model.mapper.toModel
import app.mp.model.model.LocalTrack
import app.mp.model.model.Track
import app.mp.model.repo.def.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trackRepository: TrackRepository,
    audioPlayerState: AudioPlayerState,
) : ViewModel() {

    private val _trackList = MutableLiveData<List<Track>>(emptyList())
    val trackList: LiveData<List<Track>> get() = _trackList

    private val _localTrackList = MutableLiveData<List<LocalTrack>>(emptyList())
    val localTrackList: LiveData<List<LocalTrack>> get() = _localTrackList


    val playerState = audioPlayerState.playerState.asLiveData()
    val currentTrackState = audioPlayerState.currentTrackState.asLiveData()

    fun getSimilarTrack(trackId: Int = 680316) {
        viewModelScope.launch {
            trackRepository.getSimilarTracks(trackId).collect {
                when (it) {
                    is ResponseResult.Failed -> Unit
                    is ResponseResult.Success -> {
                        val trackList = it.data!!.toModel()
                        _trackList.value = _trackList.value!!.plus(trackList)
                    }

                    is ResponseResult.Unknown -> Unit
                }
            }
        }
    }

    fun getAllLocalTracks(context: Context) {
        viewModelScope.launch {
            LocalAudioStorage(context).getAllLocalAudios().collect {
                _localTrackList.value = _localTrackList.value?.plus(it)
            }
        }
    }

}