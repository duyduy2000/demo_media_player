package app.mp.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.mp.model.repo.def.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: TrackRepository
) : ViewModel() {

    data class UiState(
        val nothing: Boolean = true
    )

    var uiState = UiState()
        private set

    val allNotes = repository.allNotes.asLiveData()

}