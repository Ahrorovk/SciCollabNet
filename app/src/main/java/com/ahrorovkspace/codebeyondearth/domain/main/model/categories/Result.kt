package com.ahrorovkspace.codebeyondearth.domain.main.model.categories

data class Result(
    val childrens: List<Children>,
    val id: Int,
    val name: String,
    val slug: String
)