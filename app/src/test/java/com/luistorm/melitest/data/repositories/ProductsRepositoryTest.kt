package com.luistorm.melitest.data.repositories

import com.luistorm.melitest.data.services.ProductsService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.openMocks

class ProductsRepositoryTest {

    @Mock
    private lateinit var productsService: ProductsService

    private lateinit var productsRepository: ProductsRepository

    @Before
    fun setup() {
        openMocks(this)
        productsRepository = ProductsRepository(productsService)
    }

    @Test
    fun `when searchQuery then search the correct query`() {
        productsRepository.searchQuery("test")
        verify(productsService, times(1)).searchQuery("test")
    }

    @Test
    fun `when getItemsByCategory then get items form the correct category`() {
        productsRepository.getItemsByCategory("test")
        verify(productsService, times(1)).getItemsByCategory("test")
    }

    @Test
    fun `when getItemsBySeller then get items form the correct seller`() {
        productsRepository.getItemsBySeller("test")
        verify(productsService, times(1)).getItemsBySeller("test")
    }

    @Test
    fun `when getCategories then get the categories info`() {
        productsRepository.getCategories()
        verify(productsService, times(1)).getCategories()
    }

}