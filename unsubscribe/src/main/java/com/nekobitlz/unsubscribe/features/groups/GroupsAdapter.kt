package com.nekobitlz.unsubscribe.features.groups

import android.graphics.Color.TRANSPARENT
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import com.nekobitlz.unsubscribe.R

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nekobitlz.unsubscribe.data.models.Group
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_group.view.*
import kotlin.math.roundToInt

class GroupsAdapter(private val onClick: (Group, ClickType) -> Unit) :
    ListAdapter<Group, GroupsAdapter.GroupsViewHolder>(GroupsDiffUtil) {

    var isLongTapMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_group, parent, false)

        return GroupsViewHolder(itemView, onClick)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object GroupsDiffUtil : DiffUtil.ItemCallback<Group>() {

        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean =
            oldItem == newItem
    }

    inner class GroupsViewHolder(
        itemView: View,
        private val onClick: (Group, ClickType) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(group: Group) {
            itemView.apply {
                tv_group_name.text = group.name
                iv_group_image.cropToPadding = true

                loadImageFromUrl(iv_group_image, group.image)
                setCheckedVisibility(group, this)

                setOnClickListener {
                    if (isLongTapMode) {
                        group.isChecked = !group.isChecked
                        setCheckedVisibility(group, it)
                    } else {
                        onClick(group, ClickType.SHORT)
                    }
                }

                setOnLongClickListener setOnLongListener@{
                    group.isChecked = !group.isChecked
                    setCheckedVisibility(group, it)

                    onClick(group, ClickType.LONG)

                    return@setOnLongListener true
                }
            }
        }

        private fun setCheckedVisibility(group: Group, view: View) {
            if (group.isChecked) {
                view.iv_checked.visibility = VISIBLE
                view.iv_group_image.setBorderColor(R.color.colorChecked)
            } else {
                view.iv_checked.visibility = GONE
                view.iv_group_image.setBorderColor(R.color.colorTransparent)
                view.iv_group_image.setBorderWidth(view.resources.getDimension(R.dimen.circle_border_size_2))
            }
        }

        private fun loadImageFromUrl(imageView: ImageView, url: String) {
            val imageSize = imageView.resources.getDimension(R.dimen.group_image_size_101).roundToInt()

            return Picasso
                .get()
                .load(url)
                .resize(imageSize, imageSize)
                .centerCrop()
                .into(imageView)
        }
    }
}