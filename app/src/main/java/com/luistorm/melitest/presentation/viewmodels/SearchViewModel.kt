package com.luistorm.melitest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luistorm.melitest.domain.usecases.SearchUseCase
import com.luistorm.melitest.presentation.models.ProductInfo
import com.luistorm.melitest.presentation.utils.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val _productResults: MutableLiveData<ProductInfo> = MutableLiveData()
    val productResults: LiveData<ProductInfo> = _productResults

    fun search(query: String) {
        _productResults.value = ProductInfo.ProductLoader(true)
        searchUseCase.search(query)
            .applySchedulers()
            .subscribe({
                _productResults.postValue(ProductInfo.ProductResponse(it))
            }, {
                _productResults.postValue(ProductInfo.ProductError)
            })
    }
}