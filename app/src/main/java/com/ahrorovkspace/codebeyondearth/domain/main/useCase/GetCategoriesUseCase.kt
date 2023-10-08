package com.ahrorovkspace.codebeyondearth.domain.main.useCase

import android.util.Log
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.main.model.categories.GetCategoryResp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(token: String): Flow<Resource<GetCategoryResp>> =
        flow {
            try {
                emit(Resource.Loading<GetCategoryResp>())
                val response = repository.getCategories(token)
                emit(Resource.Success<GetCategoryResp>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<GetCategoryResp>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<GetCategoryResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<GetCategoryResp>("${e.message}"))
            }
        }
}