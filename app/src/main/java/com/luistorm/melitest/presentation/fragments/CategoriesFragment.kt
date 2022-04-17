package com.luistorm.melitest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.luistorm.melitest.R
import com.luistorm.melitest.databinding.FragmentCategoriesBinding
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.presentation.adapters.CategoriesAdapter
import com.luistorm.melitest.presentation.viewmodels.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        categoriesViewModel.getCategories()
    }

    private fun initViews() {
        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(this@CategoriesFragment.requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = CategoriesAdapter {
                goToProductDetail(it)
            }
        }
    }

    private fun goToProductDetail(product:Product) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentsContainer, ProductDetailFragment.newInstance(product), "")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initObservers() {
        categoriesViewModel.categories.observe(this.viewLifecycleOwner) {
            binding.recyclerViewCategories.apply {
                with(adapter as CategoriesAdapter) {
                    categories = it
                    notifyDataSetChanged()
                }
            }
        }
        categoriesViewModel.showLoader.observe(this.viewLifecycleOwner) { showLoader ->
            binding.lottieAnimationViewLoader.isVisible = showLoader
            binding.recyclerViewCategories.isVisible = showLoader.not()
        }
    }

    companion object {
        fun newInstance(): CategoriesFragment = CategoriesFragment()
    }
}