package com.luistorm.melitest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luistorm.melitest.R
import com.luistorm.melitest.databinding.CategoryItemViewBinding
import com.luistorm.melitest.domain.models.Product

private const val HTTP = "http://"
private const val HTTPS = "https://"

class CategoryItemAdapter(private val items: List<Product> = emptyList()): RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>() {

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
                    val imageUrl = it.thumbnail.replace(HTTP,HTTPS)
                    this.textViewTitle.text = it.title
                    Glide.with(this.root.context)
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_clock)
                        .into(binding.imageViewThumbnail)
                    this.textViewPrice.text = this.root.context
                        .getString(R.string.price, it.price.toString())
                }
            }
        }
    }
}