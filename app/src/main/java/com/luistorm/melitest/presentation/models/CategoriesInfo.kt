package com.luistorm.melitest.presentation.models

import com.luistorm.melitest.domain.models.Category

sealed class CategoriesInfo {
    class CategoriesResponse(val categories: List<Category>): CategoriesInfo()
    object CategoriesError : CategoriesInfo()
    class CategoriesLoader(val showLoader: Boolean): CategoriesInfo()
}