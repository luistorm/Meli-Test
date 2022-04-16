package com.luistorm.melitest.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.luistorm.melitest.domain.usecases.SearchUseCase
import com.luistorm.melitest.presentation.utils.applySchedulers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    fun search() {
        searchUseCase.search("Moto G100")
            .applySchedulers()
            .subscribe({
                val x = 0
            }, {
                val y = 1
            })
    }
}