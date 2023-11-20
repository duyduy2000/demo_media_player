package app.mp.model.repo.def

import app.mp.common.util.ResponseResult
import app.mp.model.model.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

//    val allTracks: Flow<List<TrackEntity>>
//    suspend fun addTrack(track: TrackEntity)
//    suspend fun updateTrack(track: TrackEntity)
//    suspend fun deleteTracks(trackList: List<TrackEntity>)

    suspend fun getTrackFromId(id: Int): Flow<ResponseResult<Track>>

}