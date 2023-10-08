package com.ahrorovkspace.codebeyondearth.domain.authorization.useCase

import android.util.Log
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.authorization.model.AuthorizationBody
import com.ahrorovkspace.codebeyondearth.domain.authorization.model.AuthorizationResp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthorizationUseCase @Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(authCodeBody: AuthorizationBody): Flow<Resource<AuthorizationResp>> =
        flow {
            try {
                emit(Resource.Loading<AuthorizationResp>())
                val response = repository.authorization(authCodeBody)
                emit(Resource.Success<AuthorizationResp>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<AuthorizationResp>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<AuthorizationResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<AuthorizationResp>("${e.message}"))
            }
        }
}