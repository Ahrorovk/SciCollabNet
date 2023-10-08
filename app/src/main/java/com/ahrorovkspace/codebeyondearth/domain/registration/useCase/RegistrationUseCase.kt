package com.ahrorovkspace.codebeyondearth.domain.registration.useCase

import android.util.Log
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.authorization.model.AuthorizationResp
import com.ahrorovkspace.codebeyondearth.domain.registration.model.RegistrationBody
import com.ahrorovkspace.codebeyondearth.domain.registration.model.RegistrationResp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(regBody: RegistrationBody): Flow<Resource<RegistrationResp>> =
        flow {
            try {
                emit(Resource.Loading<RegistrationResp>())
                val response = repository.registration(regBody)
                emit(Resource.Success<RegistrationResp>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<RegistrationResp>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<RegistrationResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<RegistrationResp>("${e.message}"))
            }
        }
}