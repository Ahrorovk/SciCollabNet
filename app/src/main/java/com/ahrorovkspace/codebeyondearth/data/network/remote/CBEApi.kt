package com.ahrorovkspace.codebeyondearth.data.network.remote

import com.ahrorovkspace.codebeyondearth.domain.application.model.PostApplicationBody
import com.ahrorovkspace.codebeyondearth.domain.application.model.PostApplicationResp
import com.ahrorovkspace.codebeyondearth.domain.authorization.model.AuthorizationBody
import com.ahrorovkspace.codebeyondearth.domain.authorization.model.AuthorizationResp
import com.ahrorovkspace.codebeyondearth.domain.main.model.categories.GetCategoryResp
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesBody
import com.ahrorovkspace.codebeyondearth.domain.main.model.project.GetProjectByCategoriesResp
import com.ahrorovkspace.codebeyondearth.domain.myApplication.model.ApproveApplicationBody
import com.ahrorovkspace.codebeyondearth.domain.myApplication.model.ApproveApplicationResp
import com.ahrorovkspace.codebeyondearth.domain.myApplication.model.GetMyApplicationResp
import com.ahrorovkspace.codebeyondearth.domain.myProject.model.GetMyProjectResp
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.ChangeProfileInfoBody
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.ChangeProfileInfoResp
import com.ahrorovkspace.codebeyondearth.domain.profileInfo.model.GetProfileInfoResp
import com.ahrorovkspace.codebeyondearth.domain.refreshToken.model.RefreshTokenBody
import com.ahrorovkspace.codebeyondearth.domain.refreshToken.model.RefreshTokenResp
import com.ahrorovkspace.codebeyondearth.domain.registration.model.RegistrationBody
import com.ahrorovkspace.codebeyondearth.domain.registration.model.RegistrationResp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface CBEApi {
    @POST("account/login/")
    suspend fun authorization(
        @Body
        authBody: AuthorizationBody
    ): AuthorizationResp

    @POST("account/register/")
    suspend fun registration(
        @Body
        regBody: RegistrationBody
    ): RegistrationResp

    @POST("projects/applications/")
    suspend fun postApplication(
        @Header("Authorization") token: String,
        @Body
        postApp: PostApplicationBody
    ): PostApplicationResp

    @POST("account/token/refresh/")
    suspend fun refreshToken(
        @Body
        refreshTokenBody: RefreshTokenBody
    ): RefreshTokenResp

    @POST("projects/applications/approve/")
    suspend fun approveApplications(
        @Header("Authorization")
        token: String,
        @Body
        approveApp: ApproveApplicationBody
    ): ApproveApplicationResp

    @PUT("account/clients/profile/")
    suspend fun changeProfileInfo(
        @Header("Authorization") token: String,
        @Body
        profileInfoBody: ChangeProfileInfoBody
    ): ChangeProfileInfoResp

    @GET("account/clients/profile/")
    suspend fun getProfileInfo(
        @Header("Authorization") token: String
    ): GetProfileInfoResp
    @GET("projects/project/")
    suspend fun getMyProject(
        @Header("Authorization") token: String
    ): GetMyProjectResp

    @GET("projects/applications/")
    suspend fun getMyApplication(
        @Header("Authorization") token: String
    ): GetMyApplicationResp

    @GET("handbook/categories/")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): GetCategoryResp

    @POST("handbook/categories/projects/")
    suspend fun getProjectByCategories(
        @Header("Authorization") token: String,
        @Body projectBody: GetProjectByCategoriesBody
    ): GetProjectByCategoriesResp
}