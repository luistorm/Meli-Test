package com.luistorm.melitest.data.models.remote

import com.google.gson.annotations.SerializedName

data class SellerResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("power_seller_status") val powerSellerStatus: String = ""
)
