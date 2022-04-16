package com.luistorm.melitest.data.models.remote

import com.google.gson.annotations.SerializedName

data class SearchQueryResponse(
    @SerializedName("query") val query: String = "",
    @SerializedName("results") val results: List<ProductItemResponse> = listOf()
)
