package com.ahrorovkspace.codebeyondearth.domain.application.useCase

import android.util.Log
import com.ahrorovkspace.codebeyondearth.core.Resource
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import com.ahrorovkspace.codebeyondearth.domain.application.model.PostApplicationBody
import com.ahrorovkspace.codebeyondearth.domain.application.model.PostApplicationResp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostApplicationUseCase@Inject constructor(
    private val repository: CBERepository
) {
    operator fun invoke(
        token: String,
        postApp: PostApplicationBody
    ): Flow<Resource<PostApplicationResp>> =
        flow {
            try {
                emit(Resource.Loading<PostApplicationResp>())
                val response = repository.postApplication(token, postApp)
                emit(Resource.Success<PostApplicationResp>(response))
            } catch (e: HttpException) {
                val gson = Gson()
                lateinit var jsonString: String;
                lateinit var response: PostApplicationResp
                e.response()?.errorBody()?.let {
                    jsonString = it.string()
                    Log.e("GSON", jsonString)
                    response = gson.fromJson(jsonString, PostApplicationResp::class.java)
                }
                emit(
                    Resource.Error<PostApplicationResp>(
                        response.toString() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<PostApplicationResp>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<PostApplicationResp>("${e.message}"))
            }
        }
}