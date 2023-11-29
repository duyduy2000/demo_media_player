package app.mp.common.util.network

sealed class ResponseResult<T>(val data: T?, val errorMessage: String?) {

    class Unknown<T> : ResponseResult<T>(data = null, errorMessage = null)
    class Success<T>(data: T) : ResponseResult<T>(data = data, errorMessage = null)
    class Failed<T>(errorMessage: String) : ResponseResult<T>(data = null, errorMessage = errorMessage)
}
