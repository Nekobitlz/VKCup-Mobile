package com.nekobitlz.products.features.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nekobitlz.products.R
import com.nekobitlz.products.data.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*
import kotlin.math.roundToInt

class ProductsAdapter(private val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductsAdapter.ProductsViewHolder>(ProductsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductsViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object ProductsDiffUtil : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem
    }

    class ProductsViewHolder(itemView: View, private val onClick: (Product) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(product: Product) {
            itemView.apply {
                iv_product_avatar.clipToOutline = true
                tv_product_name.text = product.name
                tv_product_price.text = product.price
                loadImageFromUrl(product.avatar, iv_product_avatar)
                setOnClickListener { onClick(product) }
            }
        }

        private fun loadImageFromUrl(url: String, imageView: ImageView) {
            val size = imageView.resources
                .getDimension(R.dimen.item_product_image_size_158)
                .roundToInt()

            Picasso
                .get()
                .load(url)
                .resize(size, size)
                .centerCrop()
                .into(imageView)
        }
    }
}