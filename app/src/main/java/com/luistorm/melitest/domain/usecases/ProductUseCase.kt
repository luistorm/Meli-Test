package com.luistorm.melitest.domain.usecases

import com.luistorm.melitest.data.repositories.ProductsRepository
import com.luistorm.melitest.domain.models.Product
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    fun getItemBySeller(sellerId: String, actualProductId: String): Single<List<Product>> = productsRepository.getItemsBySeller(sellerId).map {
        it.results
            .map { productItemResponse -> Product.fromResponse(productItemResponse) }
            .filter { product ->  product.id != actualProductId}
    }

}