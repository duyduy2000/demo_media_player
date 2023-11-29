package app.mp.model.repo.impl

import app.mp.common.util.network.ResponseResult
import app.mp.model.remote.api.FreesoundApi
import app.mp.model.remote.dto.AccessTokenDto
import app.mp.model.repo.def.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val api: FreesoundApi) : TokenRepository {

    private val errorLogTag = "Token Repository Error"

    override suspend fun getAccessToken(): Flow<ResponseResult<AccessTokenDto>> = flow {
//        try {
//            emit(ResponseResult.Unknown())
//
//            val response = api.getTrackById(auth = authKey)
//            if (response.body() != null && response.isSuccessful) {
//                emit(ResponseResult.Success(data = response.body()!!))
//            }
//
//        } catch (exception: IOException) {
//            Log.e(errorLogTag, "$exception")
//            emit(ResponseResult.Failed(errorMessage = "Internet connection might not available."))
//        } catch (exception: HttpException) {
//            Log.e(errorLogTag, "$exception")
//            emit(ResponseResult.Failed(errorMessage = "Unexpected response."))
//        }
    }
}