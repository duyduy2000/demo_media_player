package rokudo.silvernote.model.repo.impl

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import rokudo.silvernote.model.local.database.NoteDatabase
import rokudo.silvernote.model.local.entity.Note
import rokudo.silvernote.model.repo.def.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val database: NoteDatabase) : NoteRepository{

    override val allNotes: Flow<List<Note>> = database.noteDao().getAllNotes()

    @WorkerThread
    override suspend fun addNote(note: Note) {
        database.noteDao().insert(note)
    }

    @WorkerThread
    override suspend fun updateNote(note: Note) {
        database.noteDao().insert(note)
    }

    @WorkerThread
    override suspend fun deleteNote(noteList: List<Note>) {
        database.noteDao().delete(noteList)
    }
}