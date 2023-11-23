package app.mp.model.remote.api

import app.mp.model.remote.dto.TrackDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FreesoundApi {

    @GET("sounds/{id}/")
    suspend fun getTrackById(
        @Path("id") soundId: Int,
        @Query("token") token: String
    ): Response<TrackDto>
}