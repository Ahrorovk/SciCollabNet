package com.ahrorovkspace.codebeyondearth.domain.myProject.model

data class Result(
    val categories: List<Category>,
    val comments_count: Int,
    val description: String,
    val downloads_count: Int,
    val `file`: String,
    val id: Int,
    val images: List<Image>,
    val likes_count: Int,
    val name: String,
    val slug: String,
    val tags: List<Tag>,
    val user: User,
    val views_count: Int
)