package com.luistorm.melitest.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luistorm.melitest.databinding.CategoriesItemViewBinding
import com.luistorm.melitest.domain.models.Category

class CategoriesAdapter(var categories: List<Category> = emptyList()): RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CategoriesItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(categories[position])

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(private val binding: CategoriesItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                category.also {
                    this.lottieAnimationViewLoader.isVisible = category.products.isNullOrEmpty()
                    this.recyclerViewItems.isVisible = category.products.isNullOrEmpty().not()
                    this.textViewCategory.text = it.name
                    this.recyclerViewItems.apply {
                        layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                        setHasFixedSize(true)
                        adapter = CategoryItemAdapter(it.products)
                    }
                }
            }
        }
    }
}