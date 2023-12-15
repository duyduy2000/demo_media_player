package app.mp.model.remote.api

import app.mp.model.remote.dto.AudioDto
import app.mp.model.remote.dto.AudioListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FreesoundApi {

    @GET("sounds/{id}/")
    suspend fun getAudioById(
        @Path("id") id: Int,
        @Query("token") token: String
    ): Response<AudioDto>

    @GET("sounds/{id}/similar/")
    suspend fun getSimilarAudios(
        @Path("id") trackId: Int,
        @Query("token") token: String,
        @Query("page") pageIndex: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("fields") fields: String = AudioListDto.infoFields
    ): Response<AudioListDto>

    @GET("search/text/")
    suspend fun getAudioByTextSearch(
        @Query("query") query: String,
        @Query("token") token: String,
        @Query("page") pageIndex: Int = 1,
        @Query("page_size") pageSize: Int = 10,
        @Query("fields") fields: String = AudioListDto.infoFields
    ): Response<AudioListDto>
}