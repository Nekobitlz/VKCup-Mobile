package com.nekobitlz.products.features.shops

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nekobitlz.products.R
import com.nekobitlz.products.data.models.Shop
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_shop.view.*
import kotlin.math.roundToInt

class ShopsAdapter : ListAdapter<Shop, ShopViewHolder>(ShopDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_shop, parent, false)

        return ShopViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object ShopDiffUtil : DiffUtil.ItemCallback<Shop>() {

        override fun areItemsTheSame(oldItem: Shop, newItem: Shop): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Shop, newItem: Shop): Boolean = oldItem == newItem
    }
}

class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(shop: Shop) {
        itemView.apply {
            //  iv_shop_avatar.clipToOutline = true
            tv_shop_name.text = shop.name
            tv_shop_status.text = shop.getStatus(resources)

            loadImageFromUrl(shop.avatar, iv_shop_avatar)
        }
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        val size = imageView.resources.getDimension(R.dimen.circle_image_size_48).roundToInt()

        Picasso
            .get()
            .load(url)
            .resize(size, size)
            .centerCrop()
            .into(imageView)
    }
}