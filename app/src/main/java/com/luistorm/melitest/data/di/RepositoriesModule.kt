package com.luistorm.melitest.data.di

import com.luistorm.melitest.data.services.ProductsService
import com.luistorm.melitest.data.repositories.ProductsRepository
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