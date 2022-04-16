package com.luistorm.melitest.domain.di

import com.luistorm.melitest.data.services.ProductsService
import com.luistorm.melitest.domain.repositories.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Singleton
    @Provides
    fun providesRepository(apiService: ProductsService) = ProductsRepository(apiService)

}