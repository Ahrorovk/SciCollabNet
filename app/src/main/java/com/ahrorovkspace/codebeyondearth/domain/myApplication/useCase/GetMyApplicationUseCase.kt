package com.ahrorovkspace.codebeyondearth.domain.myApplication.useCase

import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.myApplication.model.GetMyApplicationResp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMyApplicationUseCase @Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(token: String): Flow<Resource<GetMyApplicationResp>> =
        flow {
            try {
                emit(Resource.Loading<GetMyApplicationResp>())
                val response = repository.getMyApplications(token)
                emit(Resource.Success<GetMyApplicationResp>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<GetMyApplicationResp>(
                        e.toString() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<GetMyApplicationResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<GetMyApplicationResp>("${e.message}"))
            }
        }
}