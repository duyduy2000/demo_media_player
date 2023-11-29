package app.mp.model.remote.api

import app.mp.model.remote.dto.TrackDto
import app.mp.model.remote.dto.TrackListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FreesoundApi {

    @GET("sounds/{id}/")
    suspend fun getTrackById(
        @Path("id") id: Int,
        @Query("token") token: String
    ): Response<TrackDto>

    @GET("sounds/<id>/similar/")
    suspend fun getSimilarTracks(
        @Path("id") trackId: Int,
        @Query("token") token: String,
        @Query("page") pageIndex: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("fields") fields: String = TrackListDto.trackFields
    ): Response<TrackListDto>

    @GET("search/text/")
    suspend fun getTracksByTextSearch(
        @Query("query") query: String,
        @Query("token") token: String,
        @Query("page") pageIndex: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("fields") fields: String = TrackListDto.trackFields
    ): Response<TrackListDto>
}