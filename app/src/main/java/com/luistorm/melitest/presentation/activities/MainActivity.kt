package com.luistorm.melitest.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.luistorm.melitest.R
import com.luistorm.melitest.databinding.ActivityMainBinding
import com.luistorm.melitest.presentation.fragments.CategoriesFragment
import com.luistorm.melitest.presentation.fragments.SearchResultsFragment
import com.luistorm.melitest.presentation.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToCategories()
        setListeners()
    }

    private fun setListeners() {
        binding.editTextSearch.setOnEditorActionListener { editText, actionId, keyEvent ->
            return@setOnEditorActionListener when(actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (editText.text.isNotEmpty()) {
                        editText.hideKeyboard()
                        goToSearchResults(editText.text.toString())
                    }
                    true
                }
                else -> false
            }
        }
        binding.editTextSearch.addTextChangedListener { text ->
            text?.let {
                if (it.toString().isEmpty()) {
                    goToCategories()
                }
            }
        }
    }

    private fun goToSearchResults(query: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentsContainer, SearchResultsFragment.newInstance(query), "")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun goToCategories() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentsContainer, CategoriesFragment.newInstance(), "")
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.editTextSearch.apply {
            setText("")
            hideKeyboard()
            clearFocus()
        }

    }

}