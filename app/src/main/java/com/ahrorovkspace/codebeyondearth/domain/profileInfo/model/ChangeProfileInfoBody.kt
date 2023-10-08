package com.ahrorovkspace.codebeyondearth.domain.profileInfo.model

data class ChangeProfileInfoBody(
    val categories: List<Int>,
    val first_name: String,
    val last_name: String,
    val description: String,
    val phone: String
)
