package com.ahrorovkspace.codebeyondearth.domain.profileInfo.useCase

import android.util.Log
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.GetProfileInfoResp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(token: String): Flow<Resource<GetProfileInfoResp>> =
        flow {
            try {
                emit(Resource.Loading<GetProfileInfoResp>())
                val response = repository.getProfileInfo(token)
                emit(Resource.Success<GetProfileInfoResp>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<GetProfileInfoResp>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<GetProfileInfoResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<GetProfileInfoResp>("${e.message}"))
            }
        }
}