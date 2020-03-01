package com.nekobitlz.photohandler.features.photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nekobitlz.photohandler.R
import com.nekobitlz.photohandler.data.models.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*
import kotlin.math.roundToInt

class PhotosAdapter(private val onClick: (Photo) -> Unit) :
    ListAdapter<Photo, PhotoViewHolder>(PhotosDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_photo, parent, false)

        return PhotoViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object PhotosDiffUtil : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem == newItem
    }
}

class PhotoViewHolder(itemView: View, private val onClick: (Photo) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(photo: Photo) {
        itemView.apply {
            loadImageFromUrl(photo.url, iv_photo)
            setOnClickListener { onClick(photo) }
        }
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        val size = imageView.resources
            .getDimension(R.dimen.item_photo_size_118)
            .roundToInt()

        Picasso
            .get()
            .load(url)
            .resize(size, size)
            .centerCrop()
            .into(imageView)
    }
}