package rokudo.silvernote.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import rokudo.silvernote.model.repo.def.NoteRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: NoteRepository
) : ViewModel() {

    data class UiState(
        val nothing: Boolean = true
    )

    var uiState = UiState()
        private set

    val allNotes = repository.allNotes.asLiveData()

}