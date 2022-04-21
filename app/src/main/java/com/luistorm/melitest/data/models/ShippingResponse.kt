package com.luistorm.melitest.data.models

import com.google.gson.annotations.SerializedName

data class ShippingResponse(
    @SerializedName("free_shipping") val isFreeShipping: Boolean = false
)
