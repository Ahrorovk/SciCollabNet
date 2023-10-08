package com.ahrorovkspace.codebeyondearth.domain.main.useCase

import android.util.Log
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesBody
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesResp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProjectByCategoryUseCase @Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(
        token: String,
        projectByCategoriesBody: GetProjectByCategoriesBody
    ): Flow<Resource<GetProjectByCategoriesResp>> =
        flow {
            try {
                emit(Resource.Loading<GetProjectByCategoriesResp>())
                val response = repository.getProjectByCategories(token, projectByCategoriesBody)
                emit(Resource.Success<GetProjectByCategoriesResp>(response))
            } catch (e: HttpException) {

                emit(
                    Resource.Error<GetProjectByCategoriesResp>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<GetProjectByCategoriesResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<GetProjectByCategoriesResp>("${e.message}"))
            }
        }
}