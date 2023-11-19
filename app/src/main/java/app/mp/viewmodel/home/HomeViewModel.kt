package app.mp.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.mp.common.util.ResponseResult
import app.mp.model.repo.def.TokenRepository
import app.mp.model.repo.def.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    trackRepository: TrackRepository,
    private val tokenRepository: TokenRepository,
) : ViewModel() {

    data class UiState(
        val nothing: Boolean = true
    )

    var uiState = UiState()
        private set

    val allTracks = trackRepository.allTracks.asLiveData()

    fun getAccessToken() {
        viewModelScope.launch {
            tokenRepository.getAccessToken().collect{
                when(it) {
                    is ResponseResult.Failed -> uiState
                    is ResponseResult.Success -> Log.e("token", it.data!!.toString())
                    is ResponseResult.Unknown -> uiState
                }
            }
        }
    }

}