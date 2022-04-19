package com.luistorm.melitest.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.luistorm.melitest.R
import com.luistorm.melitest.databinding.FragmentProductDetailBinding
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.presentation.adapters.CategoryItemAdapter
import com.luistorm.melitest.presentation.models.ProductInfo
import com.luistorm.melitest.presentation.utils.NEW
import com.luistorm.melitest.presentation.utils.transformToHttps
import com.luistorm.melitest.presentation.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initViews()
    }

    private fun setObservers() {
        productViewModel.productResults.observe(this.viewLifecycleOwner) {
            when (it) {
                is ProductInfo.ProductResponse -> {
                    showOrHideLoader(false)
                    drawOtherSellerProducts(it.products)
                }
                is ProductInfo.ProductLoader -> showOrHideLoader(it.showLoader)
                is ProductInfo.ProductError -> {
                    showOrHideLoader(false)
                    showOtherProductsError()
                }
            }
        }
    }

    private fun showOtherProductsError() {
        Toast.makeText(requireContext(), R.string.error_other_products, Toast.LENGTH_LONG).show()
    }

    private fun drawOtherSellerProducts(productsList: List<Product>) {
        if (productsList.isEmpty()) {
            binding.containerOtherProducts.isVisible = false
        } else {
            binding.containerOtherProducts.isVisible = true
            binding.recyclerViewOther.isVisible = true
            binding.recyclerViewOther.apply {
                adapter = CategoryItemAdapter(productsList) { product ->
                    goToProductDetail(product)
                }
            }
        }
    }

    private fun showOrHideLoader(showLoader: Boolean) {
        binding.lottieAnimationViewLoader.isVisible = showLoader
    }

    private fun goToProductDetail(product:Product) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentsContainer, newInstance(product))
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initViews() {
        binding.recyclerViewOther.apply {
            layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        arguments?.let {
            val product = it.getParcelable(PRODUCT) ?: Product()
            productViewModel.getOtherProductsFromSeller(product)
            drawInfo(product)
        }
        binding.imageViewBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun drawInfo(product: Product) {
        binding.apply {
            textViewProductTitle.text = product.title
            Glide.with(this.root.context)
                .load(product.thumbnail.transformToHttps())
                .placeholder(R.drawable.ic_clock)
                .into(binding.imageViewItem)
            textViewPrice.text = getString(R.string.price, product.price.toString())
            textViewQuantity.text = getString(R.string.available_quantity, product.availableQuantity.toString())
            textViewCondition.text = getString(if(product.condition == NEW) R.string.new_item
            else R.string.used_item)
            textViewCondition.background = ContextCompat.getDrawable(
                requireContext(), if (product.condition == NEW) R.drawable.bg_new_item
                else R.drawable.bg_used_item)
            textViewFreeDelivery.isVisible = product.isFreeShipping
            var attributes = ""
            product.attributes.forEach {
                attributes += it.name + ": " + it.valueName + "\n"
            }
            textViewAttributes.text = getString(R.string.attributes, attributes)
        }
    }



    companion object {
        private const val PRODUCT = "PRODUCT"

        fun newInstance(product: Product): ProductDetailFragment = ProductDetailFragment().apply {
            arguments = Bundle().also {
                it.putParcelable(PRODUCT, product)
            }
        }
    }
}