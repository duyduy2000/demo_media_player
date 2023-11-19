package app.mp.model.repo.def

import app.mp.model.local.entity.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

    val allTracks: Flow<List<Track>>
    suspend fun addTrack(track: Track)
    suspend fun updateTrack(track: Track)
    suspend fun deleteTracks(trackList: List<Track>)

}