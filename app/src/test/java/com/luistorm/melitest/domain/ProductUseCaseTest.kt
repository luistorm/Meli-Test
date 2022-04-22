package com.luistorm.melitest.domain

import com.luistorm.melitest.data.models.*
import com.luistorm.melitest.data.repositories.ProductsRepository
import com.luistorm.melitest.domain.usecases.ProductUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class ProductUseCaseTest {

    @MockK
    private lateinit var productsRepository: ProductsRepository

    private lateinit var productUseCase: ProductUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        productUseCase = ProductUseCase(productsRepository)
    }

    @Test
    fun `when getItemBySeller then return the correct Single`() {
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
        every { productsRepository.getItemsBySeller("test") } returns Single.just(searchQueryResponse)
        val getItemBySellerMapped = productUseCase.getItemBySeller("test", "1").blockingGet()
        verify { productsRepository.getItemsBySeller("test") }
        TestCase.assertEquals(getItemBySellerMapped[0].id, "2")
    }

}