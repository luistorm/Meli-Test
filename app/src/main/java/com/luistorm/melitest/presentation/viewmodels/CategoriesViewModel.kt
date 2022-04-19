package com.luistorm.melitest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luistorm.melitest.domain.models.Category
import com.luistorm.melitest.domain.usecases.CategoriesUseCase
import com.luistorm.melitest.presentation.models.CategoriesInfo
import com.luistorm.melitest.presentation.utils.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val categoriesUseCase: CategoriesUseCase) : ViewModel() {

    private val _categories: MutableLiveData<CategoriesInfo> = MutableLiveData()
    private lateinit var categoriesList: MutableList<Category>
    val categories: LiveData<CategoriesInfo> = _categories

    fun getCategories() {
        _categories.value = CategoriesInfo.CategoriesLoader(true)
        categoriesUseCase.getCategories()
            .applySchedulers()
            .subscribe({
                categoriesList = it.toMutableList()
                _categories.postValue(CategoriesInfo.CategoriesResponse(it))
                categoriesList.forEach { category ->
                    getItemsByCategory(category)
                }
            },{
                _categories.postValue(CategoriesInfo.CategoriesError)
            })
    }

    private fun getItemsByCategory(category: Category) {
        categoriesUseCase.getItemsByCategory(category.id)
            .applySchedulers()
            .subscribe({ products ->
                categoriesList.find { it.id == category.id }?.products = products
                _categories.postValue(CategoriesInfo.CategoriesResponse(categoriesList))
            }, {
            })
    }
}