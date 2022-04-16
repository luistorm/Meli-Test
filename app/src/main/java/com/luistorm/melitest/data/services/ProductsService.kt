package com.luistorm.melitest.data.services

import com.luistorm.melitest.data.models.remote.SearchQueryResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val SEARCH_PATH = "/sites/MCO/search"
private const val QUERY = "q"

interface ProductsService {
    @GET(SEARCH_PATH)
    fun searchQuery(
        @Query(QUERY) query: String
    ): Single<SearchQueryResponse>
}