package com.ahrorovkspace.codebeyondearth.data.network

import com.ahrorovkspace.codebeyondearth.data.network.remote.CBEApi
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
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

class CBERepositoryImpl(
    private val api: CBEApi
) : CBERepository {
    override suspend fun authorization(authBody: AuthorizationBody): AuthorizationResp =
        api.authorization(authBody)

    override suspend fun registration(regBody: RegistrationBody): RegistrationResp =
        api.registration(regBody)

    override suspend fun postApplication(
        token: String,
        postApp: PostApplicationBody
    ): PostApplicationResp =
        api.postApplication(token, postApp)

    override suspend fun refreshToken(refresh: RefreshTokenBody): RefreshTokenResp =
        api.refreshToken(refresh)

    override suspend fun getProfileInfo(token: String): GetProfileInfoResp =
        api.getProfileInfo(token)

    override suspend fun getMyApplications(token: String): GetMyApplicationResp =
        api.getMyApplication(token)

    override suspend fun getCategories(token: String): GetCategoryResp =
        api.getCategories(token)

    override suspend fun approveApplications(
        token: String,
        approveApp: ApproveApplicationBody
    ): ApproveApplicationResp =
        api.approveApplications(token, approveApp)

    override suspend fun changeProfileInfo(
        token: String,
        profileInfoBody: ChangeProfileInfoBody
    ): ChangeProfileInfoResp =
        api.changeProfileInfo(token, profileInfoBody)

    override suspend fun getMyProject(token: String): GetMyProjectResp = api.getMyProject(token)

    override suspend fun getProjectByCategories(
        token: String,
        projectBody: GetProjectByCategoriesBody
    ): GetProjectByCategoriesResp =
        api.getProjectByCategories(token, projectBody)
}