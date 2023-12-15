package app.mp.model.repo.def

import app.mp.common.util.network.ResponseResult
import app.mp.model.remote.dto.AudioDto
import app.mp.model.remote.dto.AudioListDto
import kotlinx.coroutines.flow.Flow

interface AudioRepository {

//    val allTracks: Flow<List<TrackEntity>>
//    suspend fun addTrack(track: TrackEntity)
//    suspend fun updateTrack(track: TrackEntity)
//    suspend fun deleteTracks(trackList: List<TrackEntity>)

    suspend fun getAudioFromId(id: Int): Flow<ResponseResult<AudioDto>>

    suspend fun getSimilarAudios(trackId: Int, pageIndex: Int = 1): Flow<ResponseResult<AudioListDto>>

    suspend fun getAudiosByTextSearch(query: String, pageIndex: Int = 1): Flow<ResponseResult<AudioListDto>>

}