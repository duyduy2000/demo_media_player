package rokudo.silvernote.model.repo.def

import kotlinx.coroutines.flow.Flow
import rokudo.silvernote.model.local.entity.Note

interface NoteRepository {

    val allNotes: Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteList: List<Note>)

}