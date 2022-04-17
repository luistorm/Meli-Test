package com.luistorm.melitest.domain.usecases

import com.luistorm.melitest.data.repositories.ProductsRepository
import com.luistorm.melitest.domain.models.Product
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    fun search(query: String): Single<List<Product>> = productsRepository.searchQuery(query).map {
        it.results.map { productResponse -> Product.fromResponse(productResponse) }
    }

}