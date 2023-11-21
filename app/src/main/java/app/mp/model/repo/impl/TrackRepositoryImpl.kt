package app.mp.model.repo.impl

import android.util.Log
import app.mp.common.util.ResponseResult
import app.mp.common.util.getApiKey
import app.mp.model.model.Track
import app.mp.model.remote.FreesoundApi
import app.mp.model.repo.def.TrackRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(private val api: FreesoundApi) : TrackRepository {
    private val errorLogTag = "Track Repository Error"

    override suspend fun getTrackFromId(id: Int): Flow<ResponseResult<Track>> = flow {
        try {
            emit(ResponseResult.Unknown())

            val response = api.getTrackById(id, token = getApiKey())
            if (response.body() != null && response.isSuccessful) {
                emit(ResponseResult.Success(data = response.body()!!))
            }

        } catch (exception: IOException) {
            Log.e(errorLogTag, "$exception")
            emit(ResponseResult.Failed(errorMessage = "Internet connection might not available."))
        } catch (exception: HttpException) {
            Log.e(errorLogTag, "$exception")
            emit(ResponseResult.Failed(errorMessage = "Unexpected response."))
        }
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