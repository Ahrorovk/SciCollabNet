package com.ahrorovkspace.codebeyondearth.domain

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

interface CBERepository {
    suspend fun authorization(authBody: AuthorizationBody): AuthorizationResp
    suspend fun registration(regBody: RegistrationBody): RegistrationResp
    suspend fun postApplication(token:String, postApp: PostApplicationBody): PostApplicationResp
    suspend fun refreshToken(refresh: RefreshTokenBody): RefreshTokenResp
    suspend fun getProfileInfo(token: String): GetProfileInfoResp
    suspend fun getMyApplications(token: String): GetMyApplicationResp
    suspend fun getCategories(token: String): GetCategoryResp
    suspend fun changeProfileInfo(
        token: String,
        profileInfoBody: ChangeProfileInfoBody
    ): ChangeProfileInfoResp

    suspend fun getMyProject(token: String): GetMyProjectResp

    suspend fun approveApplications(
        token: String,
        approveApp: ApproveApplicationBody
    ): ApproveApplicationResp

    suspend fun getProjectByCategories(
        token: String,
        projectBody: GetProjectByCategoriesBody
    ): GetProjectByCategoriesResp
}