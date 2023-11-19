package app.mp.model.repo.impl

import android.util.Base64
import android.util.Log
import app.mp.common.util.ResponseResult
import app.mp.model.model.AccessToken
import app.mp.model.remote.SpotifyApi
import app.mp.model.repo.def.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(private val api: SpotifyApi) : TokenRepository {

    private val errorLogTag = "Token Repository Error"

    override suspend fun getAccessToken(): Flow<ResponseResult<AccessToken>> = flow {
        try {
            emit(ResponseResult.Unknown())
            val clientId = "a5e1ee2b857444058a7d63ff00fe501e"
            val clientSecret = "bcf194022f914351aae3ad80a703fa45"
            val authKey = "Basic ${
                Base64.encodeToString(
                    "$clientId:$clientSecret".toByteArray(),
                    Base64.NO_WRAP
                )
            }"
            val response = api.getAccessToken(auth = authKey)
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