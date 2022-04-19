package com.luistorm.melitest.presentation.models

import com.luistorm.melitest.domain.models.Product

sealed class ProductInfo {
    class ProductResponse(val products: List<Product>): ProductInfo()
    object ProductError : ProductInfo()
    class ProductLoader(val showLoader: Boolean): ProductInfo()
}