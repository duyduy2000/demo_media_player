package app.mp.model.remote

import app.mp.model.model.AccessToken
import retrofit2.Response
import retrofit2.http.POST

interface SpotifyApi {

    @POST("token")
    suspend fun getAccessToken(): Response<AccessToken>

}