package com.luistorm.melitest.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.luistorm.melitest.domain.models.Category
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.domain.usecases.CategoriesUseCase
import com.luistorm.melitest.presentation.models.CategoriesInfo
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

class CategoriesViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulersRules()

    @MockK
    private lateinit var categoriesUseCase: CategoriesUseCase

    private val categoriesObserver: Observer<CategoriesInfo> = mock()

    private lateinit var categoriesViewModel: CategoriesViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        categoriesViewModel = CategoriesViewModel(categoriesUseCase)
    }

    @Test
    fun `when getCategories and result is ok then return the correct value`() {
        every { categoriesUseCase.getCategories() } returns Single.just(listOf(Category("1")))
        every { categoriesUseCase.getItemsByCategory("1") } returns Single.just(listOf(Product("2")))
        categoriesViewModel.categories.observeForever(categoriesObserver)
        categoriesViewModel.getCategories()
        val categoriesCaptor = ArgumentCaptor.forClass(categoriesViewModel.categories.value?.javaClass)
        categoriesCaptor.run {
            verify(categoriesObserver, times(3)).onChanged(this.capture())
            when (this.value) {
                is CategoriesInfo.CategoriesLoader -> Assert.assertEquals(this.value, true)
                else -> {
                    this.value?.let { categoriesInfo ->
                        Assert.assertEquals((categoriesInfo as CategoriesInfo.CategoriesResponse).categories[0].id, "1")
                        if (categoriesInfo.categories[0].products.isNotEmpty()) {
                            Assert.assertEquals(categoriesInfo.categories[0].products[0].id, "2")
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `when getCategories and the result are error then return the correct value`() {
        every { categoriesUseCase.getCategories() } returns Single.error(Throwable())
        categoriesViewModel.categories.observeForever(categoriesObserver)
        categoriesViewModel.getCategories()
        val categoriesCaptor = ArgumentCaptor.forClass(categoriesViewModel.categories.value?.javaClass)
        categoriesCaptor.run {
            verify(categoriesObserver, times(2)).onChanged(this.capture())
            when (this.value) {
                is CategoriesInfo.CategoriesLoader -> Assert.assertEquals(this.value, true)
                else -> {
                    Assert.assertEquals(this.value, CategoriesInfo.CategoriesError)
                }
            }
        }
    }

}