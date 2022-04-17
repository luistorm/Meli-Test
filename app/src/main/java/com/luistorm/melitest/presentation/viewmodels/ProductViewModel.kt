package com.luistorm.melitest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luistorm.melitest.domain.models.Category
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.domain.usecases.ProductUseCase
import com.luistorm.melitest.presentation.utils.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productUseCase: ProductUseCase) : ViewModel() {

    private val _showLoader: MutableLiveData<Boolean> = MutableLiveData()
    val showLoader: LiveData<Boolean> = _showLoader
    private val _sellerProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val sellerProducts: LiveData<List<Product>> = _sellerProducts

    fun getOtherProductsFromSeller(product: Product) {
        _showLoader.value = true
        productUseCase.getItemBySeller(product.seller.id, product.id)
            .applySchedulers()
            .subscribe({
                _sellerProducts.postValue(it)
                _showLoader.postValue(false)
            }, {
                _showLoader.postValue(false)
            })
    }

}