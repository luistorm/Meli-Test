package com.luistorm.melitest.domain.usecases

import com.luistorm.melitest.data.repositories.ProductsRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    fun search(query: String) = productsRepository.searchQuery(query)

}