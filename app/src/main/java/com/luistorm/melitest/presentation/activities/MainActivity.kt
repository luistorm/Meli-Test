package com.luistorm.melitest.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luistorm.melitest.R
import com.luistorm.melitest.databinding.ActivityMainBinding
import com.luistorm.melitest.presentation.fragments.CategoriesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToCategories()
    }

    private fun goToCategories() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentsContainer, CategoriesFragment.newInstance(), "")
        transaction.commit()
    }

}