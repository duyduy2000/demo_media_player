package app.mp.viewmodel.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mp.common.util.ResponseResult
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

    fun getTrack() {
        viewModelScope.launch {
            trackRepository.getTrackFromId(680316).collect{
                when(it) {
                    is ResponseResult.Failed -> uiState
                    is ResponseResult.Success -> Log.e("track", it.data!!.toString())
                    is ResponseResult.Unknown -> uiState
                }
            }
        }
    }

}