package com.luistorm.melitest.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luistorm.melitest.domain.models.Category
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.domain.usecases.SearchUseCase
import com.luistorm.melitest.presentation.utils.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    private val _searchResults: MutableLiveData<List<Product>> = MutableLiveData()
    val searchResults: LiveData<List<Product>> = _searchResults
    private val _showLoader: MutableLiveData<Boolean> = MutableLiveData()
    val showLoader: LiveData<Boolean> = _showLoader

    fun search(query: String) {
        _showLoader.value = true
        searchUseCase.search(query)
            .applySchedulers()
            .subscribe({
                _searchResults.postValue(it)
                _showLoader.postValue(false)
            }, {
                _showLoader.postValue(false)
            })
    }
}