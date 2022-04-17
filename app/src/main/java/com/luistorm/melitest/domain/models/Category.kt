package com.luistorm.melitest.domain.models

data class Category(
    val id: String = "",
    val name: String = "",
    var products: List<Product> = listOf()
)
