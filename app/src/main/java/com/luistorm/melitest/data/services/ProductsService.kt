package com.luistorm.melitest.data.services

import com.luistorm.melitest.data.models.remote.CategoryResponse
import com.luistorm.melitest.data.models.remote.SearchQueryResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val SEARCH_PATH = "search"
private const val CATEGORIES_PATH = "categories"
private const val QUERY = "q"
private const val CATEGORY = "category"
private const val SELLER_ID = "seller_id"

interface ProductsService {

    @GET(SEARCH_PATH)
    fun searchQuery(
        @Query(QUERY) query: String
    ): Single<SearchQueryResponse>

    @GET(SEARCH_PATH)
    fun getItemsByCategory(
        @Query(CATEGORY) category: String
    ): Single<SearchQueryResponse>

    @GET(CATEGORIES_PATH)
    fun getCategories(): Single<List<CategoryResponse>>

    @GET(SEARCH_PATH)
    fun getItemsBySeller(
        @Query(SELLER_ID) sellerId: String
    ): Single<SearchQueryResponse>
}