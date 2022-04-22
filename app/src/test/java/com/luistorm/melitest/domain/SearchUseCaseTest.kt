package com.luistorm.melitest.domain

import com.luistorm.melitest.data.models.*
import com.luistorm.melitest.data.repositories.ProductsRepository
import com.luistorm.melitest.domain.usecases.SearchUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class SearchUseCaseTest {

    @MockK
    private lateinit var productsRepository: ProductsRepository

    private lateinit var searchUseCase: SearchUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        searchUseCase = SearchUseCase(productsRepository)
    }

    @Test
    fun `when search then return the correct Single`() {
        val searchQueryResponse = SearchQueryResponse(
            "test",
            listOf(
                ProductItemResponse("1",
                    "Pato",
                    1.0,
                    10,
                    "new",
                    "url",
                    SellerResponse("2", "platino"),
                    ShippingResponse(true),
                    listOf(AttributeResponse("3", "hola", "hola"))
                ),
                ProductItemResponse("2",
                    "Pato",
                    1.0,
                    10,
                    "new",
                    "url",
                    SellerResponse("2", "platino"),
                    ShippingResponse(true),
                    listOf(AttributeResponse("3", "hola", "hola"))
                )
            )
        )
        every { productsRepository.searchQuery("test") } returns Single.just(searchQueryResponse)
        val searchMapped = searchUseCase.search("test").blockingGet()
        verify { productsRepository.searchQuery("test") }
        TestCase.assertEquals(searchMapped[0].id, "1")
    }

}