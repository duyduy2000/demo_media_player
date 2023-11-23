package app.mp.model.repo.def

import app.mp.common.util.ResponseResult
import app.mp.model.remote.dto.AccessTokenDto
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    suspend fun getAccessToken(): Flow<ResponseResult<AccessTokenDto>>
}