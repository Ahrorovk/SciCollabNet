package com.ahrorovkspace.codebeyondearth.domain.application.model

data class PostApplicationResp(
    val description: String,
    val id: Int,
    val project: Project,
    val user: UserX
)