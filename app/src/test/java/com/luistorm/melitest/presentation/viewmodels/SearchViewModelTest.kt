package com.luistorm.melitest.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.domain.usecases.SearchUseCase
import com.luistorm.melitest.presentation.models.ProductInfo
import com.luistorm.melitest.presentation.testrules.RxSchedulersRules
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor

class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulersRules()

    @MockK
    private lateinit var searchUseCase: SearchUseCase

    private val productsObserver: Observer<ProductInfo> = mock()

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        searchViewModel = SearchViewModel(searchUseCase)
    }

    @Test
    fun `when search and the result are ok then return the correct value`() {
        val productsList = listOf(Product("1"))
        every { searchUseCase.search("test") } returns Single.just(productsList)
        searchViewModel.productResults.observeForever(productsObserver)
        searchViewModel.search("test")
        val productsCaptor = ArgumentCaptor.forClass(searchViewModel.productResults.value?.javaClass)
        productsCaptor.run {
            verify(productsObserver, times(2)).onChanged(this.capture())
            when (this.value) {
                is ProductInfo.ProductLoader -> assertEquals(this.value, true)
                else -> {
                    this.value?.let {
                        assertEquals((it as ProductInfo.ProductResponse).products[0].id, "1")
                    }
                }
            }

        }
    }

    @Test
    fun `when search and the result are error then return the correct value`() {
        every { searchUseCase.search("test") } returns Single.error(Throwable())
        searchViewModel.productResults.observeForever(productsObserver)
        searchViewModel.search("test")
        val productsCaptor = ArgumentCaptor.forClass(searchViewModel.productResults.value?.javaClass)
        productsCaptor.run {
            verify(productsObserver, times(2)).onChanged(this.capture())
            when (this.value) {
                is ProductInfo.ProductLoader -> assertEquals(this.value, true)
                else -> {
                    assertEquals(this.value, ProductInfo.ProductError)
                }
            }

        }
    }

}