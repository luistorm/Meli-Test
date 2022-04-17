package com.luistorm.melitest.domain.models

import com.luistorm.melitest.data.models.remote.ProductItemResponse

data class Product(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val availableQuantity: Int = -1,
    val condition: String = "",
    val thumbnail: String = "",
    val isFreeShipping: Boolean = false
) {
    companion object {
        fun fromResponse(productItemResponse: ProductItemResponse): Product {
            return Product(id = productItemResponse.id,
                title = productItemResponse.title,
                price =  productItemResponse.price,
                availableQuantity = productItemResponse.availableQuantity,
                condition = productItemResponse.condition,
                thumbnail =  productItemResponse.thumbnail,
                isFreeShipping = productItemResponse.shipping.isFreeShipping)
        }
    }
}
