package com.luistorm.melitest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luistorm.melitest.R
import com.luistorm.melitest.databinding.CategoryItemViewBinding
import com.luistorm.melitest.domain.models.Product
import com.luistorm.melitest.presentation.utils.transformToHttps

class CategoryItemAdapter(private val items: List<Product> = emptyList(),
                          private val onItemSelectedListener: (Product) -> Unit): RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CategoryItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: CategoryItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                product.also {
                    this.textViewTitle.text = it.title
                    Glide.with(this.root.context)
                        .load(it.thumbnail.transformToHttps())
                        .placeholder(R.drawable.ic_clock)
                        .into(binding.imageViewThumbnail)
                    this.textViewPrice.text = this.root.context
                        .getString(R.string.price, it.price.toString())
                    this.cardViewCategoryItem.setOnClickListener { onItemSelectedListener(product) }
                }
            }
        }
    }
}