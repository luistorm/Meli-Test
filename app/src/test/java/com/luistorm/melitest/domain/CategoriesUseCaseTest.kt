package com.luistorm.melitest.domain

import com.luistorm.melitest.data.models.SearchQueryResponse
import com.luistorm.melitest.data.models.ProductItemResponse
import com.luistorm.melitest.data.models.SellerResponse
import com.luistorm.melitest.data.models.ShippingResponse
import com.luistorm.melitest.data.models.AttributeResponse
import com.luistorm.melitest.data.models.CategoryResponse
import com.luistorm.melitest.data.repositories.ProductsRepository
import com.luistorm.melitest.domain.usecases.CategoriesUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CategoriesUseCaseTest {

    @MockK
    private lateinit var productsRepository: ProductsRepository

    private lateinit var categoriesUseCase: CategoriesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        categoriesUseCase = CategoriesUseCase(productsRepository)
    }

    @Test
    fun `when getItemsByCategory then return the correct Single`() {
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
                )
            )
        )
        every { productsRepository.getItemsByCategory("test") } returns Single.just(searchQueryResponse)
        val getItemsByCategoryMapped = categoriesUseCase.getItemsByCategory("test").blockingGet()
        verify { productsRepository.getItemsByCategory("test") }
        assertEquals(getItemsByCategoryMapped[0].id, "1")
    }

    @Test
    fun `when getCategories then return the correct Single`() {
        val categoriesResponseList = listOf(CategoryResponse(
            "1",
            "test"
        ))
        every { productsRepository.getCategories() } returns Single.just(categoriesResponseList)
        val getCategoriesMapped = productsRepository.getCategories().blockingGet()
        verify { productsRepository.getCategories() }
        assertEquals(getCategoriesMapped[0].id, "1")
    }

}