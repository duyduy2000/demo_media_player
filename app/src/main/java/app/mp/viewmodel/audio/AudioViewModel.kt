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
import app.mp.model.repo.def.AudioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val audioRepository: AudioRepository,
    audioPlayerState: AudioPlayerState,
) : ViewModel() {

    private val _AudioList = MutableLiveData<List<Audio>>(emptyList())
    val audioList: LiveData<List<Audio>> get() = _AudioList


    val playerState = audioPlayerState.playerState.asLiveData()
    val currentTrackState = audioPlayerState.currentTrackState.asLiveData()

    fun getSimilarAudio(id: Int) {
        viewModelScope.launch {
            audioRepository.getSimilarAudios(id).collect {
                when (it) {
                    is ResponseResult.Failed -> Unit
                    is ResponseResult.Success -> {
                        val trackList = it.data!!.toModel()
                        _AudioList.value = _AudioList.value!!.plus(trackList)
                    }

                    is ResponseResult.Unknown -> Unit
                }
            }
        }
    }

    fun getAllLocalAudios(context: Context) {
        viewModelScope.launch {
            LocalAudioStorage(context).getAllLocalAudios().collect {
                _AudioList.value = _AudioList.value?.plus(it)
            }
        }
    }

}