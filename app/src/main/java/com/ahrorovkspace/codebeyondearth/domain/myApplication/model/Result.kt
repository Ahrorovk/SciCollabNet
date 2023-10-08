package com.ahrorovkspace.codebeyondearth.domain.myApplication.model

data class Result(
    val description: String,
    val id: Int,
    val project: Project,
    val user: UserX
)