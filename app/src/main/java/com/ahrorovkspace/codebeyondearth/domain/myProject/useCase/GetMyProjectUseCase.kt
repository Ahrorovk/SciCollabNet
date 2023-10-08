package com.ahrorovkspace.codebeyondearth.domain.myProject.useCase

import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.myProject.model.GetMyProjectResp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMyProjectUseCase @Inject constructor(
    private val repository: CBERepository
) {

    operator fun invoke(token:String): Flow<Resource<GetMyProjectResp>> =
        flow {
            try {
                emit(Resource.Loading<GetMyProjectResp>())
                val response = repository.getMyProject(token)
                emit(Resource.Success<GetMyProjectResp>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<GetMyProjectResp>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<GetMyProjectResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<GetMyProjectResp>("${e.message}"))
            }
        }
}