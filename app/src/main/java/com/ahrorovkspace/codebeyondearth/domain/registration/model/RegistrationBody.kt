package com.ahrorovkspace.codebeyondearth.domain.registration.model

data class RegistrationBody(
    val username: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)
