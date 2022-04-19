package com.luistorm.melitest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luistorm.melitest.domain.models.Category
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.domain.usecases.ProductUseCase
import com.luistorm.melitest.presentation.models.ProductInfo
import com.luistorm.melitest.presentation.utils.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productUseCase: ProductUseCase) : ViewModel() {

    private val _productResults: MutableLiveData<ProductInfo> = MutableLiveData()
    val productResults: LiveData<ProductInfo> = _productResults

    fun getOtherProductsFromSeller(product: Product) {
        _productResults.value = ProductInfo.ProductLoader(true)
        productUseCase.getItemBySeller(product.seller.id, product.id)
            .applySchedulers()
            .subscribe({
                _productResults.postValue(ProductInfo.ProductResponse(it))
            }, {
                _productResults.postValue(ProductInfo.ProductError)
            })
    }

}