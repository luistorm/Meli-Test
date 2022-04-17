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
import com.luistorm.melitest.databinding.FragmentSearchResultsBinding
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.presentation.adapters.SearchResultsAdapter
import com.luistorm.melitest.presentation.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultsFragment : Fragment() {

    private var _binding: FragmentSearchResultsBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initViews() {
        arguments?.let {
            searchViewModel.search(it.getString(QUERY, ""))
        }
        binding.recyclerViewSearch.apply {
            layoutManager = LinearLayoutManager(this@SearchResultsFragment.requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = SearchResultsAdapter {
                goToProductDetail(it)
            }
        }
    }

    private fun initObservers() {
        searchViewModel.showLoader.observe(this.viewLifecycleOwner) { showLoader ->
            binding.lottieAnimationViewLoader.isVisible = showLoader
            binding.recyclerViewSearch.isVisible = showLoader.not()
        }
        searchViewModel.searchResults.observe(this.viewLifecycleOwner) {
            binding.recyclerViewSearch.apply {
                with(adapter as SearchResultsAdapter) {
                    searchResults = it
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun goToProductDetail(product: Product) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentsContainer, ProductDetailFragment.newInstance(product), "")
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {

        private const val QUERY = "QUERY"

        fun newInstance(query: String): SearchResultsFragment = SearchResultsFragment().apply {
            arguments = Bundle().also {
                it.putString(QUERY, query)
            }
        }
    }
}