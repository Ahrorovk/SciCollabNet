package com.ahrorovkspace.codebeyondearth.domain.profileInfo.useCase

import android.util.Log
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.ChangeProfileInfoBody
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.ChangeProfileInfoResp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChangeProfileInfoUseCase @Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(
        token: String,
        profileInfoBody: ChangeProfileInfoBody
    ): Flow<Resource<ChangeProfileInfoResp>> =
        flow {
            try {
                emit(Resource.Loading<ChangeProfileInfoResp>())
                val response = repository.changeProfileInfo(token, profileInfoBody)
                emit(Resource.Success<ChangeProfileInfoResp>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<ChangeProfileInfoResp>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<ChangeProfileInfoResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<ChangeProfileInfoResp>("${e.message}"))
            }
        }
}