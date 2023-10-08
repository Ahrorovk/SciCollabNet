package com.ahrorovkspace.codebeyondearth.domain.profileInfo.model

data class GetProfileInfoResp(
    val categories: List<Int>,
    val avatar: String,
    val last_name:String,
    val first_name: String,
    val description: String,
    val user:User
)
data class User(
    val username: String,
    val email: String,
    val phone: String
)
