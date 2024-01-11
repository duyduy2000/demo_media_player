package app.mp.viewmodel.audio

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
import app.mp.model.model.Audio
import app.mp.model.model.LocalAudio
import app.mp.model.repo.def.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    audioPlayerState: AudioPlayerState,
) : ViewModel() {

    private val _audioList = MutableLiveData<List<Audio>>(emptyList())
    val audioList: LiveData<List<Audio>> get() = _audioList

    private val _localAudioList = MutableLiveData<List<LocalAudio>>(emptyList())
    val localAudioList: LiveData<List<LocalAudio>> get() = _localAudioList


    val playerState = audioPlayerState.playerState.asLiveData()
    val currentTrackState = audioPlayerState.currentTrackState.asLiveData()

    fun getSimilarAudio(id: Int = 680316) {
        viewModelScope.launch {
            audioRepository.getSimilarAudios(id).collect {
                when (it) {
                    is ResponseResult.Failed -> Unit
                    is ResponseResult.Success -> {
                        val trackList = it.data!!.toModel()
                        _audioList.value = _audioList.value!!.plus(trackList)
                    }

                    is ResponseResult.Unknown -> Unit
                }
            }
        }
    }

    fun getAllLocalAudios(context: Context) {
        viewModelScope.launch {
            LocalAudioStorage(context).getAllLocalAudios().collect {
                _localAudioList.value = _localAudioList.value?.plus(it)
            }
        }
    }

}