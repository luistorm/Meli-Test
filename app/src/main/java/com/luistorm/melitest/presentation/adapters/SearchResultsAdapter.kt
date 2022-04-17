package com.luistorm.melitest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luistorm.melitest.R
import com.luistorm.melitest.databinding.SearchItemViewBinding
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.presentation.utils.NEW
import com.luistorm.melitest.presentation.utils.transformToHttps

class SearchResultsAdapter(var searchResults: List<Product> = emptyList(),
                           private val onItemSelectedListener: (Product) -> Unit): RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            SearchItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(searchResults[position])

    override fun getItemCount(): Int = searchResults.size

    inner class ViewHolder(private val binding: SearchItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                product.also {
                    val imageUrl = it.thumbnail.transformToHttps()
                    this.textViewTitle.text = it.title
                    Glide.with(this.root.context)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_clock)
                        .into(binding.imageViewThumbnail)
                    this.textViewPrice.text = this.root.context
                        .getString(R.string.price, it.price.toString())
                    this.textViewQuantity.text = this.root.context
                        .getString(R.string.available_quantity, it.availableQuantity.toString())
                    this.textViewCondition.text = this.root.context
                        .getString(if(it.condition == NEW) R.string.new_item else R.string.used_item)
                    this.textViewCondition.background = ContextCompat.getDrawable(
                        this.root.context, if (it.condition == NEW) R.drawable.bg_new_item
                    else R.drawable.bg_used_item)
                    this.textViewFreeDelivery.isVisible = it.isFreeShipping
                    this.cardViewSearchItem.setOnClickListener { onItemSelectedListener(product) }
                }
            }
        }
    }
}