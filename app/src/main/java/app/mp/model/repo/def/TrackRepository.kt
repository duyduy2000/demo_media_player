package app.mp.model.repo.def

import app.mp.common.util.network.ResponseResult
import app.mp.model.remote.dto.TrackDto
import app.mp.model.remote.dto.TrackListDto
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

//    val allTracks: Flow<List<TrackEntity>>
//    suspend fun addTrack(track: TrackEntity)
//    suspend fun updateTrack(track: TrackEntity)
//    suspend fun deleteTracks(trackList: List<TrackEntity>)

    suspend fun getTrackFromId(id: Int): Flow<ResponseResult<TrackDto>>

    suspend fun getSimilarTracks(trackId: Int, pageIndex: Int = 1): Flow<ResponseResult<TrackListDto>>

    suspend fun getTracksByTextSearch(query: String, pageIndex: Int = 1): Flow<ResponseResult<TrackListDto>>

}