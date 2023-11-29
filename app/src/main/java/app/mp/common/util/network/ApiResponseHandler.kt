package app.mp.common.util.network

import android.util.Log
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T> handleApiCall(
    logTag: String,
    apiCall: suspend () -> Response<T>,
) = flow<ResponseResult<T>> {
    try {
        emit(ResponseResult.Unknown())
        val response = apiCall()
        if (response.body() != null && response.isSuccessful) {
            emit(ResponseResult.Success(data = response.body()!!))
        }

    } catch (exception: IOException) {
        Log.e(logTag, "$exception")
        emit(ResponseResult.Failed(errorMessage = "Internet connection might not available."))
    } catch (exception: HttpException) {
        Log.e(logTag, "$exception")
        emit(ResponseResult.Failed(errorMessage = "Unexpected response."))
    }
}