package com.luistorm.melitest.domain.di

import com.luistorm.melitest.data.repositories.ProductsRepository
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
}