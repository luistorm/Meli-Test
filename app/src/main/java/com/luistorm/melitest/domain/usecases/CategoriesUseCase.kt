package com.luistorm.melitest.domain.usecases

import com.luistorm.melitest.data.repositories.ProductsRepository
import com.luistorm.melitest.domain.models.Category
import com.luistorm.melitest.domain.models.Product
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    fun getItemsByCategory(category: String): Single<List<Product>> = productsRepository.getItemsByCategory(category).map {
        it.results.map { productItemResponse -> Product.fromResponse(productItemResponse) }
    }

    fun getCategories(): Single<List<Category>> = productsRepository.getCategories().map { categories ->
        categories.map {
            Category(id = it.id, name = it.name)
        }
    }

}