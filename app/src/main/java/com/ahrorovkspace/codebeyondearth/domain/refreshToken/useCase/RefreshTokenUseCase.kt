package com.ahrorovkspace.codebeyondearth.domain.refreshToken.useCase

import android.util.Log
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.refreshToken.model.RefreshTokenBody
import com.ahrorovkspace.codebeyondearth.domain.refreshToken.model.RefreshTokenResp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(refreshTokenBody: RefreshTokenBody): Flow<Resource<RefreshTokenResp>> =
        flow {
            try {
                emit(Resource.Loading<RefreshTokenResp>())
                val response = repository.refreshToken(refreshTokenBody)
                emit(Resource.Success<RefreshTokenResp>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<RefreshTokenResp>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<RefreshTokenResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<RefreshTokenResp>("${e.message}"))
            }
        }
}