package com.luistorm.melitest.domain.usecases

import com.luistorm.melitest.data.repositories.ProductsRepository
import com.luistorm.melitest.domain.models.Category
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    fun search(query: String) = productsRepository.searchQuery(query)

}