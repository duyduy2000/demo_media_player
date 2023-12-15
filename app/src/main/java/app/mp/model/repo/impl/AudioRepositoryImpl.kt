package app.mp.model.repo.impl

import app.mp.common.util.getApiKey
import app.mp.common.util.network.ResponseResult
import app.mp.common.util.network.handleApiCall
import app.mp.model.remote.api.FreesoundApi
import app.mp.model.remote.dto.AudioDto
import app.mp.model.remote.dto.AudioListDto
import app.mp.model.repo.def.AudioRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(private val api: FreesoundApi) : AudioRepository {
    private val errorLogTag = "Audio Repo"

    override suspend fun getAudioFromId(id: Int): Flow<ResponseResult<AudioDto>> {
        return handleApiCall(
            logTag = errorLogTag,
            apiCall = suspend { api.getAudioById(id, token = getApiKey()) }
        )
    }

    override suspend fun getSimilarAudios(
        trackId: Int,
        pageIndex: Int,
    ): Flow<ResponseResult<AudioListDto>> = handleApiCall(
        logTag = errorLogTag,
        apiCall = suspend {
            api.getSimilarAudios(
                trackId = trackId,
                token = getApiKey(),
                pageIndex = pageIndex,
            )
        }
    )

    override suspend fun getAudiosByTextSearch(
        query: String,
        pageIndex: Int,
    ): Flow<ResponseResult<AudioListDto>> = handleApiCall(
        logTag = errorLogTag,
        apiCall = suspend {
            api.getAudioByTextSearch(
                query = query,
                token = getApiKey(),
                pageIndex = pageIndex,
            )
        }
    )

}

/*
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
*/