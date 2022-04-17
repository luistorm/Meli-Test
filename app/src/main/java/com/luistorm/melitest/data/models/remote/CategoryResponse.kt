package com.luistorm.melitest.data.models.remote

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = ""
)
