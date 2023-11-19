package app.mp.model.repo.impl

import androidx.annotation.WorkerThread
import app.mp.model.local.database.TrackDatabase
import app.mp.model.local.entity.Track
import app.mp.model.repo.def.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(private val database: TrackDatabase) : TrackRepository{

    override val allTracks: Flow<List<Track>> = database.noteDao().getAllTracks()

    @WorkerThread
    override suspend fun addTrack(track: Track) {
        database.noteDao().insert(track)
    }

    @WorkerThread
    override suspend fun updateTrack(track: Track) {
        database.noteDao().insert(track)
    }

    @WorkerThread
    override suspend fun deleteTracks(trackList: List<Track>) {
        database.noteDao().delete(trackList)
    }
}