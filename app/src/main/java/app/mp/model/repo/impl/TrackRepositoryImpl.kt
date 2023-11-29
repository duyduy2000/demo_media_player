package app.mp.model.repo.impl

import app.mp.common.util.getApiKey
import app.mp.common.util.network.ResponseResult
import app.mp.common.util.network.handleApiCall
import app.mp.model.remote.api.FreesoundApi
import app.mp.model.remote.dto.TrackDto
import app.mp.model.remote.dto.TrackListDto
import app.mp.model.repo.def.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(private val api: FreesoundApi) : TrackRepository {
    private val errorLogTag = "Track Repository Error"

    override suspend fun getTrackFromId(id: Int): Flow<ResponseResult<TrackDto>> {
        return handleApiCall(
            logTag = errorLogTag,
            apiCall = suspend { api.getTrackById(id, token = getApiKey()) }
        )
    }

    override suspend fun getSimilarTracks(trackId: Int): Flow<ResponseResult<TrackListDto>> {
        return handleApiCall(
            logTag = errorLogTag,
            apiCall = suspend { api.getSimilarTracks(trackId = trackId, token = getApiKey()) }
        )
    }

    override suspend fun getTracksByTextSearch(query: String): Flow<ResponseResult<TrackListDto>> {
        return handleApiCall(
            logTag = errorLogTag,
            apiCall = suspend { api.getTracksByTextSearch(query = query, token = getApiKey()) }
        )
    }

}

/*
class TrackRepositoryImpl @Inject constructor(private val database: TrackDatabase) : TrackRepository{

override val allTracks: Flow<List<TrackEntity>> = database.trackDao().getAllTracks()

@WorkerThread
override suspend fun addTrack(track: TrackEntity) {
database.trackDao().insert(track)
}

@WorkerThread
override suspend fun updateTrack(track: TrackEntity) {
database.trackDao().insert(track)
}

@WorkerThread
override suspend fun deleteTracks(trackList: List<TrackEntity>) {
database.trackDao().delete(trackList)
}
}
*/