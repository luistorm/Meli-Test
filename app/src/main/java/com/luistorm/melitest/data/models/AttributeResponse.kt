package com.luistorm.melitest.data.models

import com.google.gson.annotations.SerializedName

data class AttributeResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("value_name") val valueName: String? = ""
)
