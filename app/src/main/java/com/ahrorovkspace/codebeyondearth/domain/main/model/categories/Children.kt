package com.ahrorovkspace.codebeyondearth.domain.main.model.categories

data class Children(
    val childrens: List<Children>,
    val id: Int,
    val name: String,
    val slug: String
)