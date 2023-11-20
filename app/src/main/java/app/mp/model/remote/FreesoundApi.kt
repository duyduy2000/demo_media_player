package app.mp.model.remote

import app.mp.model.model.Track
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FreesoundApi {

    @GET("sounds/{id}/")
    suspend fun getTrackById(
        @Path("id") soundId: Int,
        @Query("token") token: String
    ): Response<Track>
}