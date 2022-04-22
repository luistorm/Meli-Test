package com.luistorm.melitest.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.domain.models.Seller
import com.luistorm.melitest.domain.usecases.ProductUseCase
import com.luistorm.melitest.presentation.models.ProductInfo
import com.luistorm.melitest.presentation.testrules.RxSchedulersRules
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.rxjava3.core.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor

class ProductViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulersRules()

    @MockK
    private lateinit var productUseCase: ProductUseCase

    private val productsObserver: Observer<ProductInfo> = mock()

    private lateinit var productsViewModel: ProductViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        productsViewModel = ProductViewModel(productUseCase)
    }

    @Test
    fun `when getOtherProductsFromSeller and the result are ok then return the correct value`() {
        val product = Product("1", seller = Seller("1"))
        val productsList = listOf(product)
        every { productUseCase.getItemBySeller("1", "1") } returns Single.just(productsList)
        productsViewModel.productResults.observeForever(productsObserver)
        productsViewModel.getOtherProductsFromSeller(product)
        val productsCaptor = ArgumentCaptor.forClass(productsViewModel.productResults.value?.javaClass)
        productsCaptor.run {
            verify(productsObserver, times(2)).onChanged(this.capture())
            when (this.value) {
                is ProductInfo.ProductLoader -> Assert.assertEquals(this.value, true)
                else -> {
                    this.value?.let {
                        Assert.assertEquals((it as ProductInfo.ProductResponse).products[0].id, "1")
                    }
                }
            }
        }
    }

    @Test
    fun `when search and the result are error then return the correct value`() {
        every { productUseCase.getItemBySeller("1", "1") } returns Single.error(Throwable())
        productsViewModel.productResults.observeForever(productsObserver)
        productsViewModel.getOtherProductsFromSeller(Product("1", seller = Seller("1")))
        val productsCaptor = ArgumentCaptor.forClass(productsViewModel.productResults.value?.javaClass)
        productsCaptor.run {
            verify(productsObserver, times(2)).onChanged(this.capture())
            when (this.value) {
                is ProductInfo.ProductLoader -> Assert.assertEquals(this.value, true)
                else -> {
                    Assert.assertEquals(this.value, ProductInfo.ProductError)
                }
            }

        }
    }

}