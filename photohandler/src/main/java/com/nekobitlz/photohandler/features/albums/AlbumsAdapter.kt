package com.nekobitlz.photohandler.features.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nekobitlz.photohandler.R
import com.nekobitlz.photohandler.data.models.Album
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_album.view.*
import kotlin.math.roundToInt

class AlbumsAdapter(private val onClick: (Album) -> Unit) :
    ListAdapter<Album, AlbumsAdapter.AlbumsViewHolder>(AlbumsDiffUtil) {

    var isEditableMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_album, parent, false)

        return AlbumsViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object AlbumsDiffUtil : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean =
            oldItem == newItem

    }

    inner class AlbumsViewHolder(
        itemView: View,
        private val onClick: (Album) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(album: Album) {
            itemView.apply {
                iv_album_photo.clipToOutline = true
                tv_album_title.text = album.name
                tv_album_photo_count.text = album.getPhotoCount(resources)
                loadImageFromUrl(album.photo, iv_album_photo)
                setOnClickListener { onClick(album) }

                if (isEditableMode) {
                    iv_album_remove.visibility = View.VISIBLE
                } else {
                    iv_album_remove.visibility = View.GONE
                }
            }
        }

        private fun loadImageFromUrl(url: String, imageView: ImageView) {
            val size = imageView.resources
                .getDimension(R.dimen.album_photo_size_158)
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