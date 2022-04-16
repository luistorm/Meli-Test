package com.luistorm.melitest.domain.repositories

import com.luistorm.melitest.data.services.ProductsService

class ProductsRepository(private val productsService: ProductsService) {

    fun searchQuery(query: String) = productsService.searchQuery(query)

}