package rokudo.silvernote.model.repo.impl

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import rokudo.silvernote.model.local.dao.NoteDao
import rokudo.silvernote.model.local.entities.Note
import rokudo.silvernote.model.repo.def.NoteRepository

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository{

    override val allNotes: Flow<List<Note>> = dao.getAllNotes()

    @WorkerThread
    override suspend fun addNote(note: Note) {
        dao.insert(note)
    }

    @WorkerThread
    override suspend fun updateNote(note: Note) {
        dao.insert(note)
    }

    @WorkerThread
    override suspend fun deleteNote(noteList: List<Note>) {
        dao.delete(noteList)
    }
}