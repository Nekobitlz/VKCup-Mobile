package com.nekobitlz.documentslist.features

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nekobitlz.documentslist.R
import com.nekobitlz.documentslist.data.VKDocument
import com.nekobitlz.documentslist.data.VkDocumentType.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_document.view.*

class DocumentsAdapter : ListAdapter<VKDocument, DocumentsViewHolder>(DocumentsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_document, parent, false)
        return DocumentsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DocumentsDiffUtil : DiffUtil.ItemCallback<VKDocument>() {

        override fun areItemsTheSame(oldItem: VKDocument, newItem: VKDocument): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: VKDocument, newItem: VKDocument): Boolean =
            oldItem == newItem
    }
}

class DocumentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(document: VKDocument) {
        itemView.apply {
            tv_filename.text = document.title
            tv_parameters.text = document.getParameters()
            iv_placeholder.clipToOutline = true

            if (document.tags.isBlank()) {
                tv_tags.visibility = GONE
            } else {
                tv_tags.text = document.tags
            }

            when (document.getType()) {
                TEXT -> iv_placeholder.setImageResource(R.drawable.ic_placeholder_document_text_72)
                ARCHIVE -> iv_placeholder.setImageResource(R.drawable.ic_placeholder_document_archive_72)
                GIF -> loadImageFromUrl(document.url, iv_placeholder)
                IMAGE -> iv_placeholder.setImageResource(R.drawable.ic_placeholder_document_image_72)
                AUDIO -> iv_placeholder.setImageResource(R.drawable.ic_placeholder_document_music_72)
                VIDEO -> iv_placeholder.setImageResource(R.drawable.ic_placeholder_document_video_72)
                BOOK -> iv_placeholder.setImageResource(R.drawable.ic_placeholder_document_book_72)
                OTHERS -> iv_placeholder.setImageResource(R.drawable.ic_placeholder_document_other_72)
            }
        }
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) = Picasso
        .get()
        .load(url)
        .resize(imageView.width, imageView.height)
        .centerCrop()
        .into(imageView)
}