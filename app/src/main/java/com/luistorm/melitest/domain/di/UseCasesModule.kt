package com.luistorm.melitest.domain.di

import com.luistorm.melitest.data.repositories.ProductsRepository
import com.luistorm.melitest.domain.usecases.CategoriesUseCase
import com.luistorm.melitest.domain.usecases.ProductUseCase
import com.luistorm.melitest.domain.usecases.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun providesSearchUseCase(productsRepository: ProductsRepository) = SearchUseCase(productsRepository)

    @Singleton
    @Provides
    fun providesCategoriesUseCase(productsRepository: ProductsRepository) = CategoriesUseCase(productsRepository)

    @Singleton
    @Provides
    fun providesProductUseCase(productsRepository: ProductsRepository) = ProductUseCase(productsRepository)
}