package com.luistorm.melitest.domain.models

import android.os.Parcelable
import com.luistorm.melitest.data.models.remote.ProductItemResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val availableQuantity: Int = -1,
    val condition: String = "",
    val thumbnail: String = "",
    val isFreeShipping: Boolean = false,
    val attributes: List<Attribute> = listOf(),
    val seller: Seller = Seller()
) : Parcelable {
    companion object {
        fun fromResponse(productItemResponse: ProductItemResponse): Product {
            return Product(id = productItemResponse.id,
                title = productItemResponse.title,
                price =  productItemResponse.price,
                availableQuantity = productItemResponse.availableQuantity,
                condition = productItemResponse.condition,
                thumbnail =  productItemResponse.thumbnail,
                isFreeShipping = productItemResponse.shipping.isFreeShipping,
                attributes = productItemResponse.attribute.map {
                    Attribute(it.id, it.name, it.valueName.orEmpty())
                },
                seller = Seller(productItemResponse.seller.id,
                    productItemResponse.seller.powerSellerStatus)
            )
        }
    }
}
