package com.luistorm.melitest.data.models.remote

import com.google.gson.annotations.SerializedName

data class ProductItemResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("price") val price: Double = 0.0,
    @SerializedName("available_quantity") val availableQuantity: Int = -1,
    @SerializedName("condition") val condition: String = "",
    @SerializedName("thumbnail") val thumbnail: String = "",
    @SerializedName("seller") val seller: SellerResponse = SellerResponse(),
    @SerializedName("shipping") val shipping: ShippingResponse = ShippingResponse(),
    @SerializedName("attributes") val attribute: List<AttributeResponse> = listOf()
)
